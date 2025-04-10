package eu.pb4.extdrawpatch.mixin.mod.block.be;

import eu.pb4.extdrawpatch.impl.model.CompactingDrawerModel;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import io.github.mattidragon.extendeddrawers.block.entity.CompactingDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.StorageDrawerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
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

    @Inject(method = "readComponents", at = @At("TAIL"))
    private void onReadComponents(ComponentsAccess components, CallbackInfo ci) {
        if (this.model != null) {
            this.model.update((CompactingDrawerBlockEntity) (Object) this);
        }
    }

    @Override
    public void onListenerUpdate(WorldChunk chunk) {
        var x = BlockAwareAttachment.get(chunk, this.pos);
        if (x != null && x.holder() instanceof CompactingDrawerModel model) {
            this.model = model;
            model.update((CompactingDrawerBlockEntity) (Object) this);
        }
    }
}
