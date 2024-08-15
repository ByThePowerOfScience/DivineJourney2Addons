package btpos.dj2addons;

import btpos.dj2addons.asmducks.InfusionStabilizerDelegateDuck;
import thaumcraft.api.crafting.IInfusionStabiliserExt;

public class test implements InfusionStabilizerDelegateDuck {
	private final IInfusionStabiliserExt internal = retrieveLogic();
	
	@Override
	public IInfusionStabiliserExt getDelegate() {
		return internal;
	}
}
