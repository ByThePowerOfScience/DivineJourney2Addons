package btpos.dj2addons.optimizations.mixin.actuallyadditions;

import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.ConnectionPair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Equality commutative
 */
@Mixin(value = ConnectionPair.class, remap = false)
public abstract class MConnectionPair {
	@Shadow @Final private BlockPos[] positions;
	@Shadow private LaserType type;
	
	@SuppressWarnings({"MissingUnique", "AddedMixinMembersNamePattern"})
	int hashCode = -1;
	
	@Override
	public int hashCode() {
		if (hashCode == -1) {
			int hash = 0;
			for (BlockPos position : positions) {
				hash += position.hashCode();
			}
			hash *= type.ordinal() + 1;
			hashCode = hash;
		}
		return hashCode;
	}
	
	/**
	 * @author ByThePowerOfScience
	 * @reason Commutative (non-directional) pair equality for hashtables.
	 */
	@Overwrite @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConnectionPair))
			return false;
		
		ConnectionPair other = ((ConnectionPair) obj);
		BlockPos[] otherPos = other.getPositions();
		return this.type == other.getType()
		       && ((this.positions[0].equals(otherPos[0]) && this.positions[1].equals(otherPos[1]))
					|| (this.positions[0].equals(otherPos[1]) && this.positions[1].equals(otherPos[0])));
	}
	
	@Inject(
			method = "readFromNBT",
			at=@At("HEAD")
	)
	private void dj2addons$invalidateHashcode(NBTTagCompound compound, CallbackInfo ci) {
		hashCode = -1;
	}
}

