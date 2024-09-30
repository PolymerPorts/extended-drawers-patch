package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.factorytools.api.resourcepack.BaseItemProvider;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import io.github.mattidragon.extendeddrawers.item.UpgradeItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(UpgradeItem.class)
public class UpgradeItemMixin implements AutoModeledPolymerItem {
    @Unique
    private final Item polymerItem = BaseItemProvider.requestItem();

    @Override
    public Item getPolymerItem() {
        return polymerItem;
    }
}
