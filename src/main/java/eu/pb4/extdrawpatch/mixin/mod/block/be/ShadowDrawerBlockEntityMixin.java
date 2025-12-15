package eu.pb4.extdrawpatch.mixin.mod.block.be;

import eu.pb4.extdrawpatch.impl.model.DrawerModel;
import eu.pb4.extdrawpatch.impl.model.ShadowDrawerModel;
import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import io.github.mattidragon.extendeddrawers.block.entity.DrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.ShadowDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.storage.DrawerSlot;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShadowDrawerBlockEntity.class)
public class ShadowDrawerBlockEntityMixin extends BlockEntity implements BlockEntityExtraListener {
    @Shadow public ItemVariant item;
    @Shadow public long countCache;
    @Shadow private boolean hidden;
    @Unique
    private ShadowDrawerModel model;

    public ShadowDrawerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "recalculateContents", at = @At("RETURN"), remap = false)
    private void updateModel(CallbackInfo ci) {
        if (this.model != null) {
            this.model.update(this.item, this.countCache, this.hidden);
        }
    }

    @Override
    public void onListenerUpdate(LevelChunk chunk) {
        var x = BlockAwareAttachment.get(chunk, this.worldPosition);
        if (x != null && x.holder() instanceof ShadowDrawerModel model) {
            this.model = model;
            model.update(this.item, this.countCache, this.hidden);
        }
    }
}
