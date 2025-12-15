package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import io.github.mattidragon.extendeddrawers.item.UpgradeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(UpgradeItem.class)
public class UpgradeItemMixin implements PolymerItem {
    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.MUSIC_DISC_13;
    }
}
