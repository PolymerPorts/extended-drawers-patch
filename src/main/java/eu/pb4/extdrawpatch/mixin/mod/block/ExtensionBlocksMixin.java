package eu.pb4.extdrawpatch.mixin.mod.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.extendeddrawers.extensions.registry.ExtensionBlocks;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(ExtensionBlocks.class)
public class ExtensionBlocksMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$Settings;create()Lnet/minecraft/block/AbstractBlock$Settings;"), require = 0)
    private static AbstractBlock.Settings makeNonOpaque(AbstractBlock.Settings original) {
        return original.nonOpaque();
    }
}
