package eu.pb4.extdrawpatch.mixin.mod;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ExtendedDrawers.class)
public class ExtendedDrawersMixin {
    @Redirect(method = "registerItemGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;register(Lnet/minecraft/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object registerGroup(Registry<Object> registry, Identifier id, Object entry) {
        PolymerItemGroupUtils.registerPolymerItemGroup(id, (ItemGroup) entry);
        return entry;
    }
}
