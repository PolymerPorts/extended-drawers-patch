package eu.pb4.extdrawpatch.mixin.mod.block;

import eu.pb4.extdrawpatch.impl.BaseFactoryBlock;
import eu.pb4.extdrawpatch.impl.model.ShadowDrawerModel;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import io.github.mattidragon.extendeddrawers.block.ShadowDrawerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(ShadowDrawerBlock.class)
public class ShadowDrawerBlockMixin implements BaseFactoryBlock {

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.END_STONE_BRICKS.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new ShadowDrawerModel(initialBlockState);
    }
}