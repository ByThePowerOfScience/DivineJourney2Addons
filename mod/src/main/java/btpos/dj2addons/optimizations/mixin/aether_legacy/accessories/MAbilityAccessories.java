package btpos.dj2addons.optimizations.mixin.aether_legacy.accessories;

import btpos.dj2addons.optimizations.mixinducks.aether_legacy.Duck_Accessories;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.abilities.AbilityAccessories;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Fixes this method creating 1000 new itemstacks every tick for LITERALLY NO REASON
 */
@Mixin(AbilityAccessories.class)
public abstract class MAbilityAccessories {
	@Shadow @Final private PlayerAether playerAether;
	@Shadow private boolean stepUpdate;
	@Shadow private boolean invisibilityUpdate;
	
	
	/**
	 * @author ByThePowerOfScience
	 * @reason Prevent a hundred itemstacks from being created and discarded per tick and lagging the server to a halt.
	 */
	@Overwrite(remap = false)
	public void onUpdate() {
		IAccessoryInventory accessories = this.playerAether.accessories;
		Duck_Accessories duckedAccessories = (Duck_Accessories) accessories;
		EntityPlayer playerEntity = this.playerAether.getEntity();
		
		if (playerEntity.ticksExisted % 400 == 0) {
			duckedAccessories.damageWornStack(1, ItemsAether.zanite_ring);
			duckedAccessories.damageWornStack(1, ItemsAether.zanite_pendant);
		}
		
		if (!playerEntity.world.isRemote && duckedAccessories.wearingAccessory(ItemsAether.ice_ring)
				|| duckedAccessories.wearingAccessory(ItemsAether.ice_pendant)) {
			int i = MathHelper.floor(playerEntity.posX);
			int j = MathHelper.floor(playerEntity.getEntityBoundingBox().minY);
			int k = MathHelper.floor(playerEntity.posZ);
			
			for (int l = i - 1; l <= i + 1; ++l) {
				for (int i1 = j - 1; i1 <= j + 1; ++i1) {
					for (int j1 = k - 1; j1 <= k + 1; ++j1) {
						IBlockState state = playerEntity.world.getBlockState((new MutableBlockPos()).setPos(l, i1, j1));
						Block block = state.getBlock();
						BlockPos pos = new BlockPos(l, i1, j1);
						if (block != Blocks.WATER && block != Blocks.FLOWING_WATER) {
							if (block != Blocks.LAVA && block != Blocks.FLOWING_LAVA) {
								continue;
							}
							
							if (state.getValue(BlockLiquid.LEVEL) == 0) {
								playerEntity.world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
							} else {
								playerEntity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
							}
						} else if (state.getValue(BlockLiquid.LEVEL) == 0) {
							playerEntity.world.setBlockState(pos, Blocks.ICE.getDefaultState());
						} else {
							playerEntity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
						}
						
						duckedAccessories.damageWornStack(1, ItemsAether.ice_ring);
						duckedAccessories.damageWornStack(1, ItemsAether.ice_pendant);
					}
				}
			}
		}
		
		if (duckedAccessories.wearingAccessory(ItemsAether.iron_bubble)) {
			playerEntity.setAir(0);
		}
		
		if (duckedAccessories.wearingAccessory(ItemsAether.agility_cape)) {
			if (!playerEntity.isSneaking()) {
				this.stepUpdate = true;
				playerEntity.stepHeight = 1.0F;
			} else if (this.stepUpdate) {
				playerEntity.stepHeight = 0.5F;
				this.stepUpdate = false;
			}
		} else if (this.stepUpdate) {
			playerEntity.stepHeight = 0.5F;
			this.stepUpdate = false;
		}
		
		if (duckedAccessories.wearingAccessory(ItemsAether.invisibility_cape)) {
			this.invisibilityUpdate = true;
			playerEntity.setInvisible(true);
		} else if (!duckedAccessories.wearingAccessory(ItemsAether.invisibility_cape)
				&& !playerEntity.isPotionActive(Potion.getPotionById(14))
				&& this.invisibilityUpdate)
		{
			playerEntity.setInvisible(false);
			this.invisibilityUpdate = false;
		}
		
		if (duckedAccessories.wearingAccessory(ItemsAether.regeneration_stone)
				&& playerEntity.getHealth() < playerEntity.getMaxHealth()
				&& playerEntity.getActivePotionEffect(MobEffects.REGENERATION) == null)
		{
			playerEntity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 0, false, false));
		}
		
		if (duckedAccessories.wearingAccessory(ItemsAether.phoenix_gloves)
				&& playerEntity.isWet()
				&& playerEntity.world.getTotalWorldTime() % 5L == 0L)
		{
			ItemStack currentPiece = accessories.getStackInSlot(6);
			accessories.damageWornStack(1, currentPiece);
			if (accessories.getStackInSlot(6) == ItemStack.EMPTY) {
				ItemStack outcomeStack = new ItemStack(ItemsAether.obsidian_gloves, 1, 0);
				EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(currentPiece), outcomeStack);
				accessories.setInventorySlotContents(6, outcomeStack);
			}
		}
		
		if ((duckedAccessories.wearingAccessory(ItemsAether.golden_feather) || duckedAccessories.wearingAccessory(ItemsAether.valkyrie_cape))
				&& !playerEntity.isElytraFlying())
		{
			if (!playerEntity.onGround
					&& playerEntity.motionY < 0.0
					&& !playerEntity.isInWater()
					&& !playerEntity.isSneaking())
			{
				playerEntity.motionY *= 0.6;
			}
			
			playerEntity.fallDistance = -1.0F;
		}
		
	}
}

