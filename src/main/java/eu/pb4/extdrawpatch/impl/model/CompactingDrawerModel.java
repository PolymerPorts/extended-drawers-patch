package eu.pb4.extdrawpatch.impl.model;

import io.github.mattidragon.extendeddrawers.block.CompactingDrawerBlock;
import io.github.mattidragon.extendeddrawers.storage.CompactingDrawerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Vec2f;

public class CompactingDrawerModel extends BaseDrawerModel {
    private final DrawerIcon top;
    private final DrawerIcon left;
    private final DrawerIcon right;
    public CompactingDrawerModel(BlockState state) {
        super(state, CompactingDrawerBlock.FACING, CompactingDrawerBlock.FACE);
        var mat = mat();
        this.top = DrawerIcon.create(this, true, new Vec2f(0, 0.25f), mat);
        this.left = DrawerIcon.create(this, true, new Vec2f(-0.25f, -0.25f), mat);
        this.right = DrawerIcon.create(this, true, new Vec2f(0.25f, -0.25f), mat);

        updateState(state);
    }

    @Override
    protected void setRotation(float pitch, float yaw) {
        super.setRotation(pitch, yaw);
        this.top.setRotation(pitch, yaw);
        this.left.setRotation(pitch, yaw);
        this.right.setRotation(pitch, yaw);
    }

    public void update(CompactingDrawerStorage storage) {
        var arr = storage.getSlotArray();
        var mat = mat();

        if (arr.length >= 1) {
            var x = arr[0];
            this.left.updateStorage(this, x.getResource(), x.getAmount(), storage, mat);
        } else {
            this.left.updateStorage(this, ItemVariant.blank(), 0, storage, mat);
        }

        if (arr.length >= 2) {
            var x = arr[1];
            this.top.updateStorage(this, x.getResource(), x.getAmount(), storage, mat);
        } else {
            this.top.updateStorage(this, ItemVariant.blank(), 0, storage, mat);
        }

        if (arr.length >= 3) {
            var x = arr[2];
            this.right.updateStorage(this, x.getResource(), x.getAmount(), storage, mat);
        } else {
            this.right.updateStorage(this, ItemVariant.blank(), 0, storage, mat);
        }
        this.tick();
    }
}
