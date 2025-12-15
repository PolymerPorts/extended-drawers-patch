package eu.pb4.extdrawpatch.mixin.mod.block.be;

import eu.pb4.extdrawpatch.impl.model.CompactingDrawerModel;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import io.github.mattidragon.extendeddrawers.block.entity.CompactingDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.StorageDrawerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompactingDrawerBlockEntity.class)
public abstract class CompactingDrawerBlockEntityMixin extends StorageDrawerBlockEntity implements BlockEntityExtraListener {
    @Unique
    private CompactingDrawerModel model;

    public CompactingDrawerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onSlotChanged(boolean sortingChanged) {
        super.onSlotChanged(sortingChanged);
        if (this.model != null) {
            this.model.update((CompactingDrawerBlockEntity) (Object) this);
        }
    }

    @Inject(method = "applyImplicitComponents", at = @At("TAIL"))
    private void onReadComponents(DataComponentGetter components, CallbackInfo ci) {
        if (this.model != null) {
            this.model.update((CompactingDrawerBlockEntity) (Object) this);
        }
    }

    @Override
    public void onListenerUpdate(LevelChunk chunk) {
        var x = BlockAwareAttachment.get(chunk, this.worldPosition);
        if (x != null && x.holder() instanceof CompactingDrawerModel model) {
            this.model = model;
            model.update((CompactingDrawerBlockEntity) (Object) this);
        }
    }
}
