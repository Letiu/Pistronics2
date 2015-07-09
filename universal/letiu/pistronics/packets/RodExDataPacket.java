package letiu.pistronics.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BExtension;
import letiu.pistronics.blocks.BExtensionPart;
import letiu.pistronics.blocks.BRod;
import letiu.pistronics.blocks.BRodPart;
import letiu.pistronics.blocks.BSailPart;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PBlock;
import letiu.pistronics.data.PTile;
import letiu.pistronics.tiles.TileElementHolder;
import letiu.pistronics.tiles.TileExtension;
import letiu.pistronics.tiles.TileMech;
import letiu.pistronics.tiles.TilePartblock;
import letiu.pistronics.tiles.TileRod;
import letiu.pistronics.tiles.TileSail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class RodExDataPacket extends LocationPacket<RodExDataPacket> {

	private boolean sticky, super_sticky, redstone, camou, redio;
	private int camouID, camouMeta, comp;
	private int color, face;

	private int part, type;
	
	public RodExDataPacket() {
		
	}
	
	private void extensionSetup(TileExtension tile, int part, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.part = part;
		this.type = 1;
		
		this.sticky = tile.sticky;
		this.super_sticky = tile.super_sticky;
		this.redstone = tile.redstone;
		this.camou = tile.camou;
		this.redio = tile.redio;
		this.camouID = tile.camouID;
		this.camouMeta = tile.camouMeta;
		this.comp = tile.getComp();
	}
	
	private void rodSetup(TileRod tile, int part, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.part = part;
		this.type = 2;
		
		this.redstone = tile.redstone;
	}
	
	private void mechSetup(TileMech tile, int part, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.part = part;
		this.type = 3;
		
		this.camou = tile.camou;
		this.camouID = tile.camouID;
		this.camouMeta = tile.camouMeta;
	}
	
	private void sailSetup(TileSail tile, int part, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.part = part;
		this.type = 4;
		
		this.color = tile.color;
		this.face = tile.face;
		
		this.camou = tile.camou;
		this.camouID = tile.camouID;
		this.camouMeta = tile.camouMeta;
	}
	
	private void cleatSetup(PTile tile, int dimID) {
		this.x = tile.tileEntity.xCoord;
		this.y = tile.tileEntity.yCoord;
		this.z = tile.tileEntity.zCoord;
		this.dimID = dimID;
		
		this.part = -2;
		this.type = 0; 
	}
	
	public RodExDataPacket(PTile tile, int part, int dimID) {
		if (tile instanceof TileExtension) extensionSetup((TileExtension) tile, part, dimID);
		else if (tile instanceof TileRod) rodSetup((TileRod) tile, part, dimID);
		else if (tile instanceof TileSail) sailSetup((TileSail) tile, part, dimID);
		else throw new IllegalArgumentException("Invalid Tile for packet!");
	}
	
	public RodExDataPacket(PTile tile, int dimID) {
		if (tile instanceof TileMech) mechSetup((TileMech) tile, -1, dimID);
		else if (tile instanceof TileExtension) extensionSetup((TileExtension) tile, -1, dimID);
		else if (tile instanceof TileRod) rodSetup((TileRod) tile, -1, dimID);
		else throw new IllegalArgumentException("Invalid Tile for packet!");
	}
	
	public RodExDataPacket(PTile elementTile, int dimID, boolean inMachine) {
		if (inMachine && elementTile instanceof TileMech) {
			elementTile = ((TileMech) elementTile).getPElementTile();
		}
		if (elementTile != null) {
			if (elementTile instanceof TileExtension) extensionSetup((TileExtension) elementTile, inMachine ? -2 : -1, dimID);
			if (elementTile instanceof TileRod) rodSetup((TileRod) elementTile, inMachine ? -2 : -1, dimID);
		}
	}
	
	public RodExDataPacket(TileElementHolder elementTile, int dimID, boolean inMachine, boolean clear) {
		cleatSetup(elementTile, dimID);
	}
	
	@Override
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeByte(part);
		out.writeByte(type);
		
		if (type == 1) {
			out.writeBoolean(redstone);
			out.writeBoolean(sticky);
			out.writeBoolean(super_sticky);
			out.writeBoolean(redstone);
			out.writeBoolean(camou);
			out.writeBoolean(redio);
			out.writeInt(camouID);
			out.writeByte(camouMeta);	
			out.writeByte(comp);
		}
		else if (type == 2) {
			out.writeBoolean(redstone);
		}
		else if (type == 3) {
			out.writeBoolean(camou);
			out.writeInt(camouID);
			out.writeByte(camouMeta);
		}
		else if (type == 4) {
			out.writeByte(color);
			out.writeByte(face);
			out.writeBoolean(camou);
			out.writeInt(camouID);
			out.writeByte(camouMeta);
		}
	}
	
	@Override
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		this.part = in.readByte();
		this.type = in.readByte();
		
		
		
		if (type == 1) {
			this.redstone = in.readBoolean();
			this.sticky = in.readBoolean();
			this.super_sticky = in.readBoolean();
			this.redstone = in.readBoolean();
			this.camou = in.readBoolean();
			this.redio = in.readBoolean();
			this.camouID = in.readInt();
			this.camouMeta = in.readByte();	
			this.comp = in.readByte();
		}
		else if (type == 2) {
			this.redstone = in.readBoolean();
		}
		else if (type == 3) {
			this.camou = in.readBoolean();
			this.camouID = in.readInt();
			this.camouMeta = in.readByte();
		}
		else if (type == 4) {
			this.color = in.readByte();
			this.face = in.readByte();
			this.camou = in.readBoolean();
			this.camouID = in.readInt();
			this.camouMeta = in.readByte();
		}
	}
	
	/** Data is copied to the tile */
	private void copyExtensionData(TileExtension tile1, RodExDataPacket packet) {
		tile1.sticky = packet.sticky;
		tile1.super_sticky = packet.super_sticky;
		tile1.redstone = packet.redstone;
		tile1.camou = packet.camou;
		tile1.redio = packet.redio;
		tile1.camouID = packet.camouID;
		tile1.camouMeta = packet.camouMeta;
		tile1.setComp(packet.comp);
	}
	
	/** Data is copied to the tile */
	private void copyRodData(TileRod tile1, RodExDataPacket packet) {
		tile1.redstone = packet.redstone;
	}
	
	private void copyMechData(TileMech tile1, RodExDataPacket packet) {
		tile1.camou = packet.camou;
		tile1.camouID = packet.camouID;
		tile1.camouMeta = packet.camouMeta;
	}
	
	private void copySailData(TileSail tile1, RodExDataPacket packet) {
		tile1.color = packet.color;
		tile1.face = packet.face;
		tile1.camou = packet.camou;
		tile1.camouID = packet.camouID;
		tile1.camouMeta = packet.camouMeta;
	}
	
	@Override
	public void execute(RodExDataPacket packet, EntityPlayer player, Side side) {
		this.x = packet.x;
		this.y = packet.y;
		this.z = packet.z;
		World world = player.worldObj;
		
		if (side == Side.CLIENT && packet.dimID == player.dimension) {
			PTile pTile = WorldUtil.getPTile(world, x, y, z);
			if (pTile != null) {
				if (packet.part == -2) {
					if (pTile instanceof TileElementHolder) {
						TileElementHolder tile = (TileElementHolder) pTile;
						if (packet.type == 0) {
							tile.clear();
						}
						else if (packet.type == 1) {
							PBlock element = tile.getPElement();
							if (element == null || !(element instanceof BExtension)) {
								tile.setElement(BlockData.extension);
							}
							copyExtensionData((TileExtension) tile.getPElementTile(), packet);
						}
						else if (packet.type == 2) {
							PBlock element = tile.getPElement();
							if (element == null || !(element instanceof BRod)) {
								tile.setElement(BlockData.rod);
							}
							copyRodData((TileRod) tile.getPElementTile(), packet);
						}
					}
				}
				else if (packet.part == -1) {
					if (pTile instanceof TileMech) {
						copyMechData((TileMech) pTile, packet);
					}
					else if (pTile instanceof TileExtension) {
						copyExtensionData((TileExtension) pTile, packet);
					}
					else if (pTile instanceof TileRod) {
						copyRodData((TileRod) pTile, packet);
					}
				}
				else if (pTile instanceof TilePartblock) {
					TilePartblock tile = (TilePartblock) pTile;
					
					PBlock part = tile.getPart(packet.part);
					if (packet.type == 1) {
						if (part == null || !(part instanceof BExtensionPart)) {
							tile.setPart(BlockData.extensionPart, packet.part);
						}
						PTile partTile = tile.getTile(packet.part);
						if (partTile != null && partTile instanceof TileExtension) {
							copyExtensionData((TileExtension) partTile, packet);
						}
					}
					else if (packet.type == 2) {
						if (part == null || !(part instanceof BRodPart)) {
							tile.setPart(BlockData.rodPart, packet.part);
						}
						PTile partTile = tile.getTile(packet.part);
						if (partTile != null && partTile instanceof TileRod) {
							copyRodData((TileRod) partTile, packet);
						}
					}
					else if (packet.type == 4) {
						if (part == null || !(part instanceof BSailPart)) {
							tile.setPart(BlockData.sailPart, packet.part);
						}
						PTile partTile = tile.getTile(packet.part);
						if (partTile != null && partTile instanceof TileSail) {
							copySailData((TileSail) partTile, packet);
						}
					}
					
				}
				WorldUtil.updateBlock(world, x, y, z);
			}
		}
	}
}
