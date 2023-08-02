package btpos.dj2addons.api.mixin.bewitchment;

import com.bewitchment.api.registry.Ritual;
import com.bewitchment.client.integration.jei.BewitchmentJEI;
import net.minecraftforge.registries.IForgeRegistry;
import btpos.dj2addons.api.impl.bewitchment.VModRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(BewitchmentJEI.class)
public class MBewitchmentJEI {
	@Redirect(
			remap=false,
			method="register",
			slice=@Slice(
					from=@At(
							value="CONSTANT",
							args={
									"classValue=com/bewitchment/api/registry/Ritual",
									"ordinal=1"
							}
					),
					to=@At(
							target = "mezz/jei/api/IModRegistry.addRecipes (Ljava/util/Collection;Ljava/lang/String;)V",
							value="INVOKE"
					)
			),
			at=@At(
					target="net/minecraftforge/registries/IForgeRegistry.getValuesCollection ()Ljava/util/Collection;",
					value="INVOKE"
			)
	)
	public Collection<Ritual> removeDummyRitualRecipes(IForgeRegistry<Ritual> instance) {
		return instance.getValuesCollection().stream().filter(o -> !(o instanceof VModRecipes.DummyRitual)).collect(Collectors.toSet());
	}
}
