package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.extdrawpatch.impl.ui.CapacityLimiterGui;
import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.factorytools.api.resourcepack.BaseItemProvider;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LimiterItem.class)
public class LimiterItemMixin implements AutoModeledPolymerItem {
    @Unique
    private final Item polymerItem = BaseItemProvider.requestItem();

    @Override
    public Item getPolymerItem() {
        return polymerItem;
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;useBook(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)V"))
    private void openUi(PlayerEntity instance, ItemStack book, Hand hand) {
        if (instance instanceof ServerPlayerEntity player) {
            new CapacityLimiterGui(player, book, hand);
        }
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isClient()Z"))
    private boolean alwaysClient(World instance) {
        return true;
    }
}
