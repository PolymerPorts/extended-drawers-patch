package eu.pb4.extdrawpatch.impl.model;

import io.github.mattidragon.extendeddrawers.block.DrawerBlock;
import io.github.mattidragon.extendeddrawers.storage.DrawerSlot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;

public class DrawerModel extends BaseDrawerModel {
    private final DrawerIcon[] icons;
    public DrawerModel(BlockState state) {
        super(state, DrawerBlock.FACING,  DrawerBlock.FACE);
        this.icons = switch (((DrawerBlock) state.getBlock()).slots) {
            case 1 -> new DrawerIcon[] { DrawerIcon.create(this, false, Vec2.ZERO, mat()) };
            case 2 -> new DrawerIcon[] {
                    DrawerIcon.create(this, true, new Vec2(-0.25f, 0), mat()),
                    DrawerIcon.create(this, true, new Vec2(0.25f, 0), mat()),
            };
            case 4 -> new DrawerIcon[] {
                    DrawerIcon.create(this, true, new Vec2(-0.25f, 0.25f), mat()),
                    DrawerIcon.create(this, true, new Vec2(0.25f, 0.25f), mat()),
                    DrawerIcon.create(this, true, new Vec2(-0.25f, -0.25f), mat()),
                    DrawerIcon.create(this, true, new Vec2(0.25f, -0.25f), mat()),
            };
            default -> new DrawerIcon[0];
        };

        updateState(state);
    }

    @Override
    protected void setRotation(float pitch, float yaw) {
        super.setRotation(pitch, yaw);
        for (var i : icons) {
            i.setRotation(pitch, yaw);
        }
    }

    public void update(DrawerSlot[] storages) {
        var mat = mat();
        for (var i = 0; i < this.icons.length; i++) {
            var storage = storages[i];
            var icon = icons[i];
            icon.updateStorage(this, storage.getResource(), storage.getAmount(), storage, mat);
        }
        this.tick();
    }
}
