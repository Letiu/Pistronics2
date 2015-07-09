package letiu.pistronics.piston;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.blocks.machines.BElementMachine;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.packets.MotionPacket;
import letiu.pistronics.packets.RedstoneInputPacket;
import letiu.pistronics.packets.SmokePacket;
import letiu.pistronics.tiles.ITransmitter;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TileRodFolder;
import letiu.pistronics.util.BlockProxy;
import letiu.pistronics.util.FacingUtil;
import letiu.pistronics.util.RotateUtil;
import letiu.pistronics.util.Vector2;
import letiu.pistronics.util.Vector3;
import letiu.pistronics.util.VectorUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import letiu.pistronics.blocks.machines.BRodFolder;

public class PistonSystem {

	public enum SystemType {
		MOVE, ROTATE, REDSTONE;
	}
	
	private SystemType type;
	
	private int dir;
	private float speed;
	
	private BlockProxy root;
	
	private ArrayList<BlockProxy> elements;
	private ArrayList<BlockProxy> specials;
	private ArrayList<BlockProxy> staticElements;

	private SystemController controller;
	
	private boolean moveAttached;
	
	public PistonSystem(BlockProxy root, int dir, float speed, SystemType type) {
		this.root = root;
		this.dir = dir;
		this.speed = speed;
		this.type = type;
				
		this.elements = new ArrayList<BlockProxy>();
		this.specials = new ArrayList<BlockProxy>();
		this.staticElements = new ArrayList<BlockProxy>();
		
		this.moveAttached = ConfigData.moveAttached;
		
//		controller = new SystemController();
//		PTile controllerTile = root.getPTile();
//		if (controllerTile != null && controllerTile instanceof TileMech) {
//			((TileMech) controllerTile).setController(controller);
//		}
		
		root.getConnectedForSystem(this, false);
	}
	
	public boolean tryMove() {
		
		if (!root.getWorld().isRemote) {
			int dimID = root.getWorld().provider.dimensionId;
			
			if (canMove()) {
				PacketHandler.sendToAllInDimension(new MotionPacket(this, dimID), dimID);
				sort();
//				dropItems();
				move();
				notifyNeighbors();
				return true;
			}
			else if (ConfigData.machineSmoke) {
				PacketHandler.sendToAllInDimension(new SmokePacket(root.getCoords(), dimID), dimID);
			}
		}
		return false;
	}

	public void clientMove() {
		sort();
		move();
	}
	
	private void move() {
		
		ArrayList<BlockProxy> mechs = new ArrayList<BlockProxy>();
		ArrayList<BlockProxy> deletes = new ArrayList<BlockProxy>();
		
		for (BlockProxy special : specials) {
			if (!staticElements.contains(special) && special.getPBlock() instanceof BElementMachine) {
				int facing = special.getFacing();
				if (facing == dir || facing == (dir ^ 1)) {
					mechs.add(special);
					
					if (!(special.getPBlock() instanceof BRodFolder) || facing == (dir ^ 1)) {
						BlockProxy neighbor = special.getNeighbor(dir ^ 1);
						deletes.add(neighbor);
					}
				}
			}
		}
		
		for (BlockProxy element : elements) {
			
			// moving all attached Blocks except for the ones behind //
			for (int i = 0; i < 6; i++) {
				if (i != (dir ^ 1)) moveAttached(element, i);
			}
			
			if (!mechs.contains(element)) {
				if (deletes.contains(element)) {
					element.clean();
				}
				else element.moveWithoutNotify(dir, speed);
			}
			else {
				if (element.getPBlock() instanceof BElementMachine) {
					TileMech tile = ((TileMech) element.getPTile());
					BlockProxy back = element.getNeighbor(dir ^ 1);
					BlockProxy front = element.getNeighbor(dir);
					
					int facing = element.getFacing();
				
					// TODO: optimize
					if (!(element.getPBlock() instanceof BRodFolder) || facing == dir) {
						if (!mechs.contains(front)) {
							tile.spawnElement(dir, speed);
						}
						if (element.getPBlock() instanceof BRodFolder) {
							tile.clear();
						}
					}
					
					if (!(element.getPBlock() instanceof BRodFolder) || facing == (dir ^ 1)) {
						if (element.getPBlock() instanceof BRodFolder) {
							TileRodFolder tileRF = (TileRodFolder) element.getPTile();
							tileRF.transferElementToInventory();
						}
						if (mechs.contains(back)) {
							PTile back_tile = back.getPTile();
							if (back_tile != null && back_tile instanceof TileMech) {
								TileMech mech_tile = (TileMech) back_tile;
								tile.setContent(mech_tile.getElement(), mech_tile.getElementMeta(), mech_tile.getElementTile());
							}
						}
						else {
							tile.setContent(back.getBlock(), back.getMetadata(), back.getTileEntity());
						}
					}
						
					tile.move(dir, speed);
				}
			}
			
			// move attached Block behind //
			moveAttached(element, dir ^ 1);
			
			// add Mover to SystemController
//			PTile moverTile = element.getNeighbor(dir).getPTile();
//			if (moverTile != null && moverTile instanceof IMover) {
//				controller.addMover((IMover) moverTile);
//			}
		}
	}
	
	private void notifyNeighbors() {
		for (BlockProxy element : elements) {
			element.notifyNeighbors(0);
		}
	}
	
	public boolean canMove() {
		
		if (elements.size() > ConfigData.maxSystemSize) return false;
		if (staticElements.contains(root)) return false;
		
		PTile rootTile = root.getPTile();
		if (rootTile instanceof TileElementHolder) {
			if (!((TileElementHolder) rootTile).hasElement()) return false;
		}
		
		for (BlockProxy element : elements) {
			
			if (dir == 0 && element.getCoords().y == 0) return false;
			if (dir == 1 && element.getCoords().y == root.getWorld().getHeight() - 2) return false;
			
			if (!ConfigData.canBlockBeMoved(element)) return false;
			
			if (staticElements.contains(element)) {
				BlockProxy front = element.getNeighbor(dir);
				if (!elements.contains(front) && front.isSolid()) {
					return false;
				}
			}
			else {
				PBlock block = element.getPBlock();
				int facing = element.getFacing();
				
				if (block != null) {
					if (block instanceof BRodFolder) {
						TileRodFolder tile = (TileRodFolder) element.getPTile();
						if (facing == dir && tile.getPInventory().isEmpty()) return false;
						if (facing == (dir ^ 1) && !tile.canTransfer()) return false;
						if (facing == (dir ^ 1)) {
							BlockProxy back = element.getNeighbor(dir ^ 1);
							if (!elements.contains(back) || !isInsertable(element, back)) {
								return false;
							}
						}
					}
					else if (block instanceof BElementMachine) {
						if (facing == dir || facing == (dir ^ 1)) {
							BlockProxy back = element.getNeighbor(dir ^ 1);
							if (!elements.contains(back) || !isInsertable(element, back)) {
								return false;
							}
						}
					}
					if (!(block instanceof BRodFolder && facing == (dir ^ 1))) {
						BlockProxy front = element.getNeighbor(dir);
						if (!elements.contains(front) && front.isSolid()) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean isInsertable(BlockProxy machine, BlockProxy neighbor) {
		
		if (machine == null || neighbor == null) return false;
		
		PBlock nBlock = neighbor.getPBlock();
		
		if (nBlock instanceof BRod) {
			return neighbor.getFacing() == dir || neighbor.getFacing() == (dir ^ 1);
		}
		
		if (nBlock instanceof BExtension) {
			int facing = neighbor.getFacing();
			return facing != dir && facing == machine.getFacing();
		}
		
		if (nBlock instanceof BElementMachine) {
			if (!staticElements.contains(neighbor)) {
				PTile nTile = neighbor.getPTile();
				if (nTile != null && nTile instanceof TileMech) {
					PBlock element = ((TileMech) nTile).getPElement();
					if (element != null && element instanceof BRod) {
						return neighbor.getFacing() == dir || neighbor.getFacing() == (dir ^ 1);
					}
				}
			}
		}
		
		return false;
	}
	
	private void sort() {
		Collections.sort(elements, new Comparator() {
			@Override
			public int compare(Object arg0, Object arg1) {
				Vector3 offset = FacingUtil.getOffsetForSide(dir);
				return Vector3.scalar(offset, ((BlockProxy)arg1).getCoords()) - Vector3.scalar(offset, ((BlockProxy)arg0).getCoords());
			}
		});
	}
	
	private void dropItems() {
		World world = root.getWorld();
		ArrayList<Vector3> surfaceBlocks = new ArrayList<Vector3>();
		
		if (!world.isRemote) {
			
			for (BlockProxy block : elements) {
				if (!isOnLine(block.getCoords(), surfaceBlocks)) {
					surfaceBlocks.add(block.getCoords());
					BlockProxy nextBlock = block.getNeighbor(dir);
					if (nextBlock.getBlock() != null && nextBlock.getMobilityFlag() == 1) {
						Vector3 coords = nextBlock.getCoords();
						int oldMetadata = WorldUtil.getBlockMeta(world, coords.x, coords.y, coords.z);
						nextBlock.getBlock().dropBlockAsItem(world, nextBlock.getCoords().x, nextBlock.getCoords().y, nextBlock.getCoords().z, oldMetadata, 0);
					}
				}
			}
		}
	}
	
	private boolean isOnLine(Vector3 coord, List<Vector3> coords) {
		for (Vector3 vec : coords) {
			if (isOnLine(vec, coord)) return true;
		}
		return false;
	}
	
	private boolean isOnLine(Vector3 coord1, Vector3 coord2) {
		Vector3 antiOffset = VectorUtil.getPositiveAntiOffset(dir);
		if (coord1.x * antiOffset.x != coord2.x * antiOffset.x) return false;
		if (coord1.y * antiOffset.y != coord2.y * antiOffset.y) return false;
		if (coord1.z * antiOffset.z != coord2.z * antiOffset.z) return false;
		return true;
	}
	
	public boolean tryRotate() {
		if (!root.getWorld().isRemote) {
			int dimID = root.getWorld().provider.dimensionId;
			
			if (canRotate()) {
				PacketHandler.sendToAllInDimension(new MotionPacket(this, dimID), dimID);
				dropItemsForRotate();
				rotate();
				return true;
			}
			else if (ConfigData.machineSmoke) {
				PacketHandler.sendToAllInDimension(new SmokePacket(root.getCoords(), dimID), dimID);
			}
		}
		return false;
		
	}
	
	public void clientRotate() {
		rotate();
	}
	
	private void rotate() {
		
		ArrayList<TileEntity> tls = new ArrayList<TileEntity>();
		
		for (BlockProxy element : elements) {
			tls.add(element.getTileEntityForRotate(root.getCoords(), dir, speed));
		}
		
		for (int i = 0; i < elements.size(); i++) {
			BlockProxy element = elements.get(i);
			PBlock block = element.getPBlock();
			
			if (block != null && block instanceof BElementMachine) {
				int facing = element.getFacing();
				if (facing == dir || facing == (dir ^ 1)) {
					if (isOnLine(element.getCoords(), root.getCoords())) {
						PTile tile = element.getPTile();
						if (tile != null && tile instanceof TileMech) {
							((TileMech) tile).rotate(dir, speed);
							continue;
						}
					}
				}
			}
			
			element.rotateWithTileEntity(root.getCoords(), dir, tls.get(i));
		}
	}
	
	public boolean canRotate() {
		if (elements.isEmpty()) return false;
		
		if (elements.size() > ConfigData.maxSystemSize) return false;
		
		// TODO: Implement rotate and move checks!
		
//		for (BlockProxy piston : pistons) {
//			TileEntityMechPiston tl = (TileEntityMechPiston) piston.getTileEntity();
//			if (tl.isExtending()) return false;
//		}
//		for (BlockProxy proxy : elements) {
//			if (proxy.getBlock() instanceof BlockMechRotator) {
//				TileEntityMechRotator tl = (TileEntityMechRotator) proxy.getTileEntity();
//				if (tl.isRotating()) return false;
//			}
//		}
		
		for (BlockProxy element : elements) {
//			if (ConfigData.isBannedBlock(element.getBlockID())) {
//				return false;
//			}
//			if (element.getPBlock() instanceof BMotionblock) {
//				return false;
//			}
			if (!canElementRotate(element, dir)) return false;
		}
		return true;
	}
	
	private boolean canElementRotate(BlockProxy element, int rotateDir) {

		if (!ConfigData.canBlockBeMoved(element)) return false;
		
		Vector3 startV3 = element.getCoords().copy();
		
		startV3.sub(root.getCoords());
		
		Vector3 endV3 = VectorUtil.rotateAround(startV3, new Vector3(0,0,0), rotateDir);
		
		int endY = root.getCoords().y + endV3.y;
		
		if (endY < 0 || endY > root.getWorld().getHeight() - 2) return false;
		
		Vector2 start = RotateUtil.get2DVectorForDir(startV3, rotateDir);
		Vector2 end = RotateUtil.get2DVectorForDir(endV3, rotateDir);
		
		if (rotateDir == 0 || rotateDir == 3 || rotateDir == 5) {
			Vector2 temp = start;
			start = end;
			end = temp;
		}
		
		ArrayList<Vector2> area_vecs = RotateUtil.getCircleSection(start, end);
		if (area_vecs == null) {
			System.out.println("Pistronics: BUG! in canRotate()");
			System.out.println("Pistronics: " + start);
			System.out.println("Pistronics: " + end);
			System.out.println("Pistronics: " + rotateDir);
		}
		ArrayList<BlockProxy> area = RotateUtil.getProxysFromV2(element.getWorld(), area_vecs, startV3, rotateDir);
		for (BlockProxy proxy : area) {
			proxy.getCoords().add(root.getCoords());
			if (proxy.isSolid() && !elements.contains(proxy)) {
				System.out.println("Proxy at " + proxy.getCoords() + " is stopping the structure!");
				return false;
			}
		}
		return true;
	}
	
	private void dropItemsForRotate() {
		for (BlockProxy element : elements) {
			dropItemForRotateElement(element);
		}
	}
	
	private void dropItemForRotateElement(BlockProxy element) {
		Vector3 startV3 = element.getCoords().copy();
		
		startV3.sub(root.getCoords());
		
		Vector3 endV3 = VectorUtil.rotateAround(startV3, new Vector3(0,0,0), dir);		
		
		Vector2 start = RotateUtil.get2DVectorForDir(startV3, dir);
		Vector2 end = RotateUtil.get2DVectorForDir(endV3, dir);
		
		if (dir == 0 || dir == 3 || dir == 5) {
			Vector2 temp = start;
			start = end;
			end = temp;
		}
		
		ArrayList<Vector2> area_vecs = RotateUtil.getCircleSection(start, end);
		if (area_vecs == null) {
			System.out.println("Pistronics: BUG! in dropItemsForRotate()");
			System.out.println("Pistronics: " + start);
			System.out.println("Pistronics: " + end);
			System.out.println("Pistronics: " + dir);
		}
		ArrayList<BlockProxy> area = RotateUtil.getProxysFromV2(element.getWorld(), area_vecs, startV3, dir);
		for (BlockProxy proxy : area) {
			proxy.getCoords().add(root.getCoords());
			if (!elements.contains(proxy)) {
				if (proxy.getBlock() != null && proxy.getMobilityFlag() == 1) {
					int oldMetadata = proxy.getMetadata();
					proxy.getBlock().dropBlockAsItem(proxy.getWorld(), proxy.getCoords().x, proxy.getCoords().y, proxy.getCoords().z, oldMetadata, 0);
					WorldUtil.setBlockToAir(proxy.getWorld(), proxy.getCoords().x, proxy.getCoords().y, proxy.getCoords().z);
				}
			}
		}
	}
	
	private void moveAttached(BlockProxy element, int side) {
		BlockProxy neighbor = element.getNeighbor(side);
		int facing = element.getFacing();
		if (moveAttached && WorldUtil.dependsOnSide(root.getWorld(), neighbor.getCoords(), side ^ 1) 
				&& !(element.getPBlock() instanceof BElementMachine 
						&& ((facing == dir || facing == (dir ^ 1)) && !staticElements.contains(element)))) {
			BlockProxy nextBlock = neighbor.getNeighbor(dir);
			if (nextBlock.isAir()) {
				neighbor.move(dir, speed);
			}
			else {
				neighbor.dropBlockAsItem();
				neighbor.clean();
			}
		}
		else if (side == dir && !element.getWorld().isRemote && neighbor.getBlock() != null 
				&& neighbor.getBlock().getMobilityFlag() == 1) {
			neighbor.dropBlockAsItem();
		}
	}
 	
	public boolean addElement(BlockProxy element, boolean strongConnection) {
		if (!elements.contains(element)) {
			elements.add(element);
			if (strongConnection) staticElements.add(element);
			return true;
		}
		else if (strongConnection && !staticElements.contains(element)) {
			staticElements.add(element);
			return true;
		}
		return false;
	}
	
	public boolean addSpecial(BlockProxy special) {
		if (specials.contains(special)) return false;
		else {
			specials.add(special);
			return true;
		}
	}
	
	public boolean addStatic(BlockProxy element) {
		if (staticElements.contains(element)) return false;
		else {
			staticElements.add(element);
			return true;
		}
	}
	
	/*
	public boolean addAttachment(BlockProxy attachment) {
		if (attachments.contains(attachment)) return false;
		else {
			attachments.add(attachment);
			return true;
		}
	}*/
	
	public boolean hasElement(BlockProxy element) {
		return elements.contains(element);
	}
	
	public boolean hasSpecial(BlockProxy special) {
		return specials.contains(special);
	}
	
	public boolean hasStatic(BlockProxy element) {
		return staticElements.contains(element);
	}
	
	public int getSystemSize() {
		return elements.size();
	}
	
	public BlockProxy getRoot() {
		return root;
	}
	
	public int getDir() {
		return dir;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public SystemType getSystemType() {
		return type;
	}
	
	public void pulse(int ticks) {
		for (BlockProxy element : elements) {
			PTile tile = element.getPTile();
			if (tile != null && tile instanceof ITransmitter) {
				((ITransmitter) tile).pulse(ticks);
				((ITransmitter) tile).notifyOutputBlocks(element);
			}
		}
	}
	
	public void setRedstoneStrengthAndOut(int strength) {
		for (BlockProxy element : elements) {
			PTile tile = element.getPTile();
			if (tile != null && tile instanceof ITransmitter) {
				((ITransmitter) tile).setStrength(strength);
				((ITransmitter) tile).setToOutput();
				((ITransmitter) tile).notifyOutputBlocks(element);
			}
		}
	}
	
//	public void promoteHighestInput() {
//		int maxStrength = 0;
//		BlockProxy bestElement = null;
//		
//		for (BlockProxy element : elements) {
//			PTile tile = element.getPTile();
//			if (tile != null && tile instanceof ITransmitter) {
//				int strength = ((ITransmitter) tile).getInputStrength(element);
//				if (strength >= maxStrength) {
//					maxStrength = strength;
//					bestElement = element;
//				}
//			}
//		}
//		
//		setToInput(bestElement, maxStrength);
//	}
	
//	private BlockProxy findHighestInput() {
//		
//		int maxStrength = 0;
//		BlockProxy bestElement = null;
//		
//		for (BlockProxy element : elements) {
//			PTile tile = element.getPTile();
//			if (tile != null && tile instanceof ITransmitter) {
//				int strength = ((ITransmitter) tile).getInputStrength(element);
//				if (strength >= maxStrength) {
//					maxStrength = strength;
//					bestElement = element;
//				}
//			}
//		}
//		
//		return bestElement;
//	}
	
	
	private void checkNextInput() {
		for (BlockProxy element : elements) {
			PTile eTile = element.getPTile();
			if (eTile != null && eTile instanceof ITransmitter) {
				ITransmitter transmitter = (ITransmitter) eTile;
				transmitter.checkNextInput();
			}
		}
	}
	
	public void checkRedstoneStructure() {
		boolean hasInput = false;
		for (BlockProxy element : elements) {
			PTile eTile = element.getPTile();
			if (eTile != null && eTile instanceof ITransmitter) {
				ITransmitter transmitter = (ITransmitter) eTile;
				if (transmitter.isInput()) hasInput = true;
			}
		}
		
		if (!hasInput) {
			setToInput(elements.get(0), 0);
		}
	}
	
	public static void checkIntegrity(BlockProxy root) {
		
		PistonSystem system = new PistonSystem(root, 0, 0, SystemType.REDSTONE);
		BlockProxy input = null;
		int strength = -1;
		
		for (BlockProxy element : system.elements) {
			PTile eTile = element.getPTile();
			if (eTile != null && eTile instanceof ITransmitter) {
				ITransmitter transmitter = (ITransmitter) eTile;
				if (transmitter.isInput()) {
					int elementStrength = transmitter.getStrength();
					if (elementStrength > strength) {
						strength = elementStrength;
						input = element;
					}
				}
			}
		}
		
		if (input != null) {
			system.setToInput(input, strength);
		}
		else {
			system.setToInput(root, 0);
		}
	}
	
	public void setToInput(BlockProxy proxy, int strength) {
	
		// Orders all extensions to check for new Inputs next Tick //
		// Important: Needs to be done before the new system strength is applied //
		for (BlockProxy element : elements) {
			if (!element.equals(proxy)) {
				PTile eTile = element.getPTile();
				if (eTile != null && eTile instanceof ITransmitter) {
					ITransmitter transmitter = (ITransmitter) eTile;
					if (transmitter.getStrength() > strength) {
						transmitter.checkNextInput();
					}
				}
			}
		}
		
		// Applies new strength //
		PTile tile = proxy.getPTile();
		if (tile != null && tile instanceof ITransmitter) {
			if (!root.getWorld().isRemote) {
				int dimID = root.getWorld().provider.dimensionId;
				PacketHandler.sendToAllInDimension(new RedstoneInputPacket(proxy.getCoords(), dimID, strength), dimID);
			}
		
			setRedstoneStrengthAndOut(strength);
			((ITransmitter) tile).setToInput();
		}
		
		
	}
}
