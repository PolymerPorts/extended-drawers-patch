package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.mattidragon.extendeddrawers.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModItems.class)
public class ModItemsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/BlockItem;"))
    private static BlockItem replaceBlockItemType(Block block, Item.Settings settings) {
        return new FactoryBlockItem((Block & PolymerBlock) block, settings);
    }


    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;"))
    private static Item replaceItemType(Item.Settings settings) {
        return new SimplePolymerItem(settings);
    }
}
