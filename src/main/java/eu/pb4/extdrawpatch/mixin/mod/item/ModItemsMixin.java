package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.SimplePolymerItem;
import io.github.mattidragon.extendeddrawers.registry.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModItems.class)
public class ModItemsMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/BlockItem;"))
    private static BlockItem replaceBlockItemType(Block block, Item.Properties settings) {
        return new FactoryBlockItem((Block & PolymerBlock) block, settings);
    }


    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;"))
    private static Item replaceItemType(Item.Properties settings) {
        return new SimplePolymerItem(settings);
    }
}
