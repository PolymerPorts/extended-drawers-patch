package eu.pb4.extdrawpatch.mixin.mod.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.extendeddrawers.registry.ModBlocks;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModBlocks.class)
public class ModBlocksMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractBlock$Settings;create()Lnet/minecraft/block/AbstractBlock$Settings;"))
    private static AbstractBlock.Settings replaceBlockItemType(AbstractBlock.Settings settings) {
        return settings.nonOpaque();
    }
}
