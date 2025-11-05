package eu.pb4.extdrawpatch.impl;

import eu.pb4.factorytools.api.block.model.generic.BSMMParticleBlock;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModel;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public record OverlayFactoryBlock() implements BaseFactoryBlock, BlockWithElementHolder, BSMMParticleBlock {
    public static final OverlayFactoryBlock INSTANCE = new OverlayFactoryBlock();

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return BlockStateModel.midRange(initialBlockState, pos);
    }
}
