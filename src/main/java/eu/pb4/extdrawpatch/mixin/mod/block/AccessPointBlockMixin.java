package eu.pb4.extdrawpatch.mixin.mod.block;

import eu.pb4.extdrawpatch.impl.BaseFactoryBlock;
import eu.pb4.extdrawpatch.impl.model.SimpleBlockModel;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import io.github.mattidragon.extendeddrawers.block.AccessPointBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(AccessPointBlock.class)
public class AccessPointBlockMixin implements BaseFactoryBlock {

    @Override
    public BlockState getPolymerBreakEventBlockState(BlockState state, PacketContext context) {
        return Blocks.IRON_BLOCK.defaultBlockState();
    }

    @Override
    public @Nullable ElementHolder createElementHolder(ServerLevel world, BlockPos pos, BlockState initialBlockState) {
        return new SimpleBlockModel(initialBlockState);
    }
}