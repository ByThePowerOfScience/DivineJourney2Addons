package org.btpos.dj2addons.mixin.def.tweaks.extrautils2;

import com.rwtema.extrautils2.backend.XUBlock;
import com.rwtema.extrautils2.fakeplayer.XUFakePlayer;
import com.rwtema.extrautils2.tile.TileAdvInteractor;
import com.rwtema.extrautils2.tile.TileUse;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.btpos.dj2addons.impl.modrefs.CThaumcraft;
import org.btpos.dj2addons.impl.modrefs.IsModLoaded;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value=TileUse.class, remap=false)
public abstract class MTileUse extends TileAdvInteractor {
    @Shadow(remap=false)
    private XUFakePlayer fakePlayer;
    
    @Shadow(remap=false) @Final private ItemStackHandler contents;
    
    @Inject(
            remap=false,
            method="operate",
            at=@At(
                    target="Lcom/rwtema/extrautils2/tile/TileUse;fakePlayer:Lcom/rwtema/extrautils2/fakeplayer/XUFakePlayer;",
                    value="FIELD",
                    ordinal=1,
                    shift= Shift.AFTER
            )
    )
    private void dj2addons$setFakePlayerKnowledge(CallbackInfoReturnable<Boolean> cir) {
        if (owner != null && IsModLoaded.thaumcraft)
                CThaumcraft.setFakePlayerKnowledge(fakePlayer, world.getPlayerEntityByUUID(owner.getId()));
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack, XUBlock xuBlock) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack, xuBlock);
        if (placer instanceof EntityPlayer) {
            owner = ((EntityPlayer)placer).getGameProfile();
        }
    }
}

