package eu.pb4.extdrawpatch.mixin.mod;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExtendedDrawers.class)
public class ExtendedDrawersMixin {
    @Redirect(method = "registerItemGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;register(Lnet/minecraft/core/Registry;Lnet/minecraft/resources/Identifier;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object registerGroup(Registry<Object> registry, Identifier id, Object entry) {
        PolymerItemGroupUtils.registerPolymerItemGroup(id, (CreativeModeTab) entry);
        return entry;
    }
}
