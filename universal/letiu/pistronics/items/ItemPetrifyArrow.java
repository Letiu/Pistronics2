package letiu.pistronics.items;

import java.util.List;

import letiu.modbase.events.IArrowEventListener;
import letiu.modbase.network.PacketHandler;
import letiu.modbase.util.InventoryUtil;
import letiu.modbase.util.ItemReference;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.config.ConfigData;
import letiu.pistronics.data.BlockData;
import letiu.pistronics.data.PItem;
import letiu.pistronics.entities.EntityPetrifyArrow;
import letiu.pistronics.packets.SmokePacket;
import letiu.pistronics.reference.Textures;
import letiu.pistronics.tiles.TileStatue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemPetrifyArrow extends PItem implements IArrowEventListener {

	public ItemPetrifyArrow() {
		this.name = "Petrify Arrow";
		this.creativeTab = true;
		this.textures = new String[1];
		this.textures[0] = Textures.PETRIFY_ARROW;
	}

	@Override
	public String getIcon(ItemStack stack, int pass) {
		return this.textures[0];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean value) {
		list.add("Petrifies weakend enemies!");
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		
		if (entity instanceof EntityLivingBase) {
			MovingObjectPosition movingobjectposition = new MovingObjectPosition(entity);
			
			// SAVE ENTITY NBT //
            NBTTagCompound nbt = new NBTTagCompound();
            if (movingobjectposition.entityHit instanceof EntityLivingBase) {
            	((EntityLivingBase) movingobjectposition.entityHit).writeEntityToNBT(nbt);
            }
			
            // CAUSE DAMAGE //
            
			if (player.capabilities.isCreativeMode) {
				movingobjectposition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)99999F);
			}
			else {
				movingobjectposition.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)2F);
				stack.stackSize--;
				if (stack.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
			
			// PETRIFICATION //
			EntityLivingBase target = (EntityLivingBase) entity;
			
			if (target.getHealth() <= 0) {
	    		World world = target.worldObj;
	    		int x = (int)target.posX;
	    		int y = (int)target.posY;
	    		int z = (int)target.posZ;
	    		
	    		WorldUtil.setBlock(world, x, y, z, BlockData.statue.block);
	    		
	    		TileStatue tile = (TileStatue) WorldUtil.getPTile(world, x, y, z);
	    		
	    		EntityLivingBase statueEntity = null;
	    		Class oclass = target.getClass();
	    		
	            if (oclass != null) {
	            	try {
						statueEntity = (EntityLivingBase) oclass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {player.worldObj});
					} 
	            	catch (Exception exception) {
			            exception.printStackTrace();
			        }
	            }
	    		
	    		if (statueEntity != null) {
	    			statueEntity.readEntityFromNBT(nbt);
	        		tile.setEntity((EntityLivingBase) statueEntity);
	        		
	        		tile.setAngle((int) (-target.rotationYaw));
	        		if (!world.isRemote && ConfigData.statueSmoke) PacketHandler.sendToAllInDimension(new SmokePacket(x, y, z, player.dimension), player.dimension);
	    		}
	    		else System.out.println("Entity was null!");
	    	}
			
			return true; 
		}
		
		return false;
	}
	
	@Override
	public void handleNockEvent(ArrowNockEvent event) {
		
		EntityPlayer player = event.entityPlayer;
		
		if (InventoryUtil.hasItemInHotbar(player, (Item) this.item)) {
			player.setItemInUse(event.result, ItemReference.BOW.getMaxItemUseDuration(event.result));
			event.setCanceled(true);
        }
	}
	
	@Override
	public void handleLooseEvent(ArrowLooseEvent event) {
		
		EntityPlayer player = event.entityPlayer;
		int charge = event.charge;
		
		if (InventoryUtil.hasItemInHotbar(player, (Item) this.item)) {
	        float f = (float)charge / 20.0F;
	        f = (f * f + f * 2.0F) / 3.0F;
	
	        if ((double)f < 0.1D)
	        {
	            return;
	        }
	
	        if (f > 1.0F)
	        {
	            f = 1.0F;
	        }
	
	        EntityPetrifyArrow entityarrow = new EntityPetrifyArrow(player.worldObj, player, f * 2.0F);
	
	        if (f == 1.0F)
	        {
	            entityarrow.setIsCritical(true);
	        }
	
	        int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, event.bow);
	
	        if (k > 0)
	        {
	            entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
	        }
	
	        int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, event.bow);
	
	        if (l > 0)
	        {
	            entityarrow.setKnockbackStrength(l);
	        }
	
	        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, event.bow) > 0)
	        {
	            entityarrow.setFire(100);
	        }
	
	        event.bow.damageItem(1, player);
	        //player.worldObj.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	
	        InventoryUtil.consumeItem(player.inventory, (Item) this.item);
	
	        if (!player.worldObj.isRemote)
	        {
	            player.worldObj.spawnEntityInWorld(entityarrow);
	        }
	        
	        event.setCanceled(true);
		}
	}
}
