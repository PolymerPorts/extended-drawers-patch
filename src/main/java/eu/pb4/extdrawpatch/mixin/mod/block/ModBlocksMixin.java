package eu.pb4.extdrawpatch.mixin.mod.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.mattidragon.extendeddrawers.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModBlocks.class)
public class ModBlocksMixin {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"))
    private static BlockBehaviour.Properties replaceBlockItemType(BlockBehaviour.Properties settings) {
        return settings.noOcclusion();
    }
}
