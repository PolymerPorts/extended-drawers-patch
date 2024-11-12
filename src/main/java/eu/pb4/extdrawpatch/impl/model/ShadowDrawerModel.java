package eu.pb4.extdrawpatch.impl.model;

import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import io.github.mattidragon.extendeddrawers.block.ShadowDrawerBlock;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec2f;

import java.util.List;

public class ShadowDrawerModel extends BaseDrawerModel {
    private final DrawerIcon icon;

    public ShadowDrawerModel(BlockState state) {
        super(state, ShadowDrawerBlock.FACING, ShadowDrawerBlock.FACE);
        this.icon = DrawerIcon.create(this, false, Vec2f.ZERO, mat());
        updateState(state);
    }

    @Override
    protected void setRotation(float pitch, float yaw) {
        super.setRotation(pitch, yaw);
        this.icon.setRotation(pitch, yaw);
    }

    public void update(ItemVariant item, long count, boolean hidden) {
        if (hidden || count == -1 || item.isBlank()) {
            this.icon.update(this, ItemStack.EMPTY, "", List.of(), mat());
        } else {
            this.icon.update(this, item.toStack(), count == -2 ? "∞"
                    : ((count != 0 || ExtendedDrawers.CONFIG.get().client().displayEmptyCount()) ? String.valueOf(count) : ""), List.of(), mat());
        }
        this.tick();
    }
}
