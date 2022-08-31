package org.btpos.dj2addons.ape.interfaces;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IInternalFluidTank {
	IFluidTank getFluidTank();
	
	FluidStack getHeldFluid();
}
