package eu.pb4.extdrawpatch.impl.model;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.TextDisplayElement;
import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import io.github.mattidragon.extendeddrawers.config.category.ClientCategory;
import io.github.mattidragon.extendeddrawers.storage.DrawerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDrawerModel extends BlockModel {
    private final ItemDisplayElement main;
    private final DirectionProperty property;
    private final EnumProperty<BlockFace> blockFace;

    public BaseDrawerModel(BlockState state, DirectionProperty directionProperty, EnumProperty<BlockFace> blockFaceProperty) {
        this.property = directionProperty;
        this.blockFace = blockFaceProperty;
        this.main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
        this.main.setScale(new Vector3f(2));
        this.addElement(this.main);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        super.notifyUpdate(updateType);
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            updateState(blockState());
        }
    }

    protected void updateState(BlockState state) {
        var yaw = state.get(this.property).asRotation();
        var pitch = switch (state.get(this.blockFace)) {
            case FLOOR -> -90;
            case CEILING -> 90;
            case WALL -> 0;
        };

        this.setRotation(pitch, yaw);
        if (this.getAttachment() != null) {
            this.tick();
        }
    }

    protected void setRotation(float pitch, float yaw) {
        this.main.setYaw(yaw);
        this.main.setPitch(pitch);
    }


    public record DrawerIcon(boolean isSmall, Vec2f offset, Vector2f rot, ItemDisplayElement mainItem, List<ItemDisplayElement> topIcons, TextDisplayElement count) {
        private static final Map<Identifier, ItemStack> ICONS = new HashMap<>();
        
        public static DrawerIcon create(BaseDrawerModel model, boolean isSmall, Vec2f offset, Matrix4f mat) {
            var icon = new DrawerIcon(isSmall, offset, new Vector2f(), ItemDisplayElementUtil.createSimple(), new ArrayList<>(),
                    new TextDisplayElement(Text.literal("0")));
            var conf = ExtendedDrawers.CONFIG.get().client().layout();

            icon.mainItem.setViewRange(0.4f);
            icon.mainItem.setModelTransformation(ModelTransformationMode.GUI);
            icon.count.setViewRange(0.4f);
            icon.count.setBackground(0);
            icon.count.setInvisible(true);
            mat.identity();
            mat.translate(offset.x, offset.y, 0.51f);
            mat.scale(conf.textScale(isSmall));
            mat.translate(0, -conf.textOffset() / 2, 0);
            mat.scale(0.5f);
            icon.count.setTransformation(mat);
            mat.identity();
            mat.translate(offset.x, offset.y, 0.505f);
            mat.rotateY(MathHelper.PI);
            mat.scale(conf.itemScale(isSmall));
            mat.scale(0.5f, 0.5f, 0.008f);
            icon.mainItem.setTransformation(mat);

            model.addElement(icon.mainItem);
            model.addElement(icon.count);

            return icon;
        }

        public void updateSettingsPos(BaseDrawerModel model, List<ItemStack> icons, Matrix4f mat) {
            if (icons.isEmpty() && this.topIcons.isEmpty()) {
                return;
            }

            var reposition = false;
            while (this.topIcons.size() > icons.size()) {
                reposition = true;
                model.removeElement(this.topIcons.removeLast());
            }

            while (this.topIcons.size() < icons.size()) {
                reposition = true;
                var e = ItemDisplayElementUtil.createSimple();
                e.setModelTransformation(ModelTransformationMode.GUI);
                e.setViewRange(0.4f);
                e.setPitch(this.rot.x);
                e.setYaw(this.rot.y);
                model.addElement(e);
                this.topIcons.add(e);
            }

            for (int i = 0; i < icons.size(); i++) {
                this.topIcons.get(i).setItem(icons.get(i));
            }

            if (reposition) {
                var conf = ExtendedDrawers.CONFIG.get().client().layout();

                var size = this.topIcons.size();
                mat.identity();
                mat.translate(offset.x, offset.y, 0.505f);
                mat.rotateY(MathHelper.PI);
                mat.scale(conf.textScale(isSmall));
                mat.translate(0, 0.40f, 0);
                mat.scale(0.15f, 0.15f, 0.01f);
                mat.translate(size / 2f - 0.5f, 0, 0);

                for (var e : this.topIcons) {
                    e.setTransformation(mat);
                    mat.translate(-1, 0, 0);
                }
            }
        }

        public void setRotation(float pitch, float yaw) {
            this.mainItem.setPitch(pitch);
            this.mainItem.setYaw(yaw);
            this.count.setPitch(pitch);
            this.count.setYaw(yaw);
            this.rot.set(pitch, yaw);
            for (var e : this.topIcons) {
                e.setPitch(pitch);
                e.setYaw(yaw);
            }
        }

        public void updateStorage(BaseDrawerModel model, ItemVariant resource, long amount, DrawerStorage storage, Matrix4f mat) {
            if (storage.isHidden()) {
                update(model, resource.toStack(), "", List.of(), mat);
                return;
            }

            ClientCategory.IconGroup config = ExtendedDrawers.CONFIG.get().client().icons();

            var icons = new ArrayList<ItemStack>();

            if (storage.isLocked()) {
                addIcon(icons, config.lockedIcon());
            }

            if (storage.isVoiding()) {
                addIcon(icons, config.voidingIcon());
            }

            if (storage.isHidden()) {
                addIcon(icons, config.hiddenIcon());
            }

            if (storage.isDuping()) {
                addIcon(icons, config.dupingIcon());
            }

            if (storage.getUpgrade() != null) {
                addIcon(icons, storage.getUpgrade().sprite);
            }

            if (storage.hasLimiter()) {
                addIcon(icons, ExtendedDrawers.id("item/limiter"));
            }

            update(model, resource.toStack(), storage.isDuping() ? "∞"
                    : ((amount != 0 || ExtendedDrawers.CONFIG.get().client().displayEmptyCount()) ? String.valueOf(amount) : ""), icons, mat);
        }

        public void update(BaseDrawerModel model, ItemStack itemStack, String count, List<ItemStack> icons, Matrix4f mat) {
            this.mainItem.setItem(itemStack);
            this.count.setText(Text.literal(count));
            this.updateSettingsPos(model, icons, mat);
        }


        private void addIcon(ArrayList<ItemStack> icons, Identifier id) {
            var val = ICONS.get(id);
            if (val == null) {
                var itemId = id;
                if (itemId.getPath().startsWith("item/")) {
                    itemId = itemId.withPath(x -> x.substring("item/".length()));
                } else if (itemId.getPath().startsWith("block/")) {
                    itemId = itemId.withPath(x -> x.substring("block/".length()));
                }

                var item = Registries.ITEM.get(itemId);
                if (item == Items.AIR) {
                    item = Items.BARRIER;
                }
                val = item.getDefaultStack();
                ICONS.put(id, val);
            }

            icons.add(val);
        }
    }
}
