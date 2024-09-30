package eu.pb4.extdrawpatch.mixin.mod.block.be;

import eu.pb4.extdrawpatch.impl.model.CompactingDrawerModel;
import eu.pb4.extdrawpatch.impl.model.ShadowDrawerModel;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import io.github.mattidragon.extendeddrawers.block.entity.CompactingDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.ShadowDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.StorageDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.storage.CompactingDrawerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompactingDrawerBlockEntity.class)
public abstract class CompactingDrawerBlockEntityMixin extends StorageDrawerBlockEntity implements BlockEntityExtraListener {
    @Shadow @Final public CompactingDrawerStorage storage;
    @Unique
    private CompactingDrawerModel model;

    public CompactingDrawerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onSlotChanged(boolean sortingChanged) {
        super.onSlotChanged(sortingChanged);
        if (this.model != null) {
            this.model.update(this.storage);
        }
    }

    @Inject(method = "readComponents", at = @At("TAIL"))
    private void onReadComponents(ComponentsAccess components, CallbackInfo ci) {
        if (this.model != null) {
            this.model.update(this.storage);
        }
    }

    @Override
    public void onListenerUpdate(WorldChunk chunk) {
        var x = BlockAwareAttachment.get(chunk, this.pos);
        if (x != null && x.holder() instanceof CompactingDrawerModel model) {
            this.model = model;
            model.update(this.storage);
        }
    }
}
