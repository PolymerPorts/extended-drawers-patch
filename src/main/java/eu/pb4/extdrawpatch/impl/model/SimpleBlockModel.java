package eu.pb4.extdrawpatch.impl.model;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public class SimpleBlockModel extends BlockModel {
    private final ItemDisplayElement main;

    public SimpleBlockModel(BlockState state) {
        this.main = ItemDisplayElementUtil.createSimple(state.getBlock().asItem());
        this.main.setScale(new Vector3f(2));
        this.addElement(this.main);
    }
}
