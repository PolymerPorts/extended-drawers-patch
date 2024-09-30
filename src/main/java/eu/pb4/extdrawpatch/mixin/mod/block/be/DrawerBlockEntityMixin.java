package eu.pb4.extdrawpatch.mixin.mod.block.be;

import eu.pb4.extdrawpatch.impl.model.DrawerModel;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import io.github.mattidragon.extendeddrawers.block.entity.DrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.storage.DrawerSlot;
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

@Mixin(DrawerBlockEntity.class)
public class DrawerBlockEntityMixin extends BlockEntity implements BlockEntityExtraListener {
    @Shadow @Final public DrawerSlot[] storages;
    @Unique
    private DrawerModel model;

    public DrawerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "onSlotChanged", at = @At("RETURN"), remap = false)
    private void updateModel(boolean sortingChanged, CallbackInfo ci) {
        if (this.model != null) {
            this.model.update(this.storages);
        }
    }

    @Override
    public void onListenerUpdate(WorldChunk chunk) {
        var x = BlockAwareAttachment.get(chunk, this.pos);
        if (x != null && x.holder() instanceof DrawerModel model) {
            this.model = model;
            model.update(this.storages);
        }
    }

    @Inject(method = "readComponents", at = @At("TAIL"))
    private void onReadComponents(ComponentsAccess components, CallbackInfo ci) {
        if (this.model != null) {
            this.model.update(this.storages);
        }
    }
}
