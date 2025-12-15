package eu.pb4.extdrawpatch.mixin.mod.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.extendeddrawers.extensions.registry.ExtensionBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(ExtensionBlocks.class)
public class ExtensionBlocksMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"), require = 0)
    private static BlockBehaviour.Properties makeNonOpaque(BlockBehaviour.Properties original) {
        return original.noOcclusion();
    }
}
