package eu.pb4.extdrawpatch.mixin.mod.block;

import eu.pb4.extdrawpatch.impl.BaseFactoryBlock;
import eu.pb4.extdrawpatch.impl.model.SimpleBlockModel;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import io.github.mattidragon.extendeddrawers.block.ConnectorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(ConnectorBlock.class)
public class ConnectorBlockMixin implements BaseFactoryBlock {

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.SPRUCE_PLANKS.getDefaultState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerWorld world, BlockPos pos, BlockState initialBlockState) {
        return new SimpleBlockModel(initialBlockState);
    }
}