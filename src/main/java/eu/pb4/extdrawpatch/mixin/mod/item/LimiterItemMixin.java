package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.extdrawpatch.impl.ui.CapacityLimiterGui;
import eu.pb4.polymer.core.api.item.PolymerItem;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(LimiterItem.class)
public class LimiterItemMixin implements PolymerItem {

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;useBook(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)V"))
    private void openUi(PlayerEntity instance, ItemStack book, Hand hand) {
        if (instance instanceof ServerPlayerEntity player) {
            new CapacityLimiterGui(player, book, hand);
        }
    }

    @Redirect(method = "use", at = @At(value = "FIELD", target = "Lnet/minecraft/util/ActionResult;SUCCESS:Lnet/minecraft/util/ActionResult$Success;"))
    private ActionResult.Success alwaysClient() {
        return ActionResult.SUCCESS_SERVER;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.MUSIC_DISC_13;
    }
}
