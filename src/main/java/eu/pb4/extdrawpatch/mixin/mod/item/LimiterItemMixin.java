package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.extdrawpatch.impl.ui.CapacityLimiterGui;
import eu.pb4.polymer.core.api.item.PolymerItem;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(LimiterItem.class)
public class LimiterItemMixin implements PolymerItem {

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;openItemGui(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)V"))
    private void openUi(Player instance, ItemStack book, InteractionHand hand) {
        if (instance instanceof ServerPlayer player) {
            new CapacityLimiterGui(player, book, hand);
        }
    }

    @Redirect(method = "use", at = @At(value = "FIELD", target = "Lnet/minecraft/world/InteractionResult;SUCCESS:Lnet/minecraft/world/InteractionResult$Success;"))
    private InteractionResult.Success alwaysClient() {
        return InteractionResult.SUCCESS_SERVER;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.MUSIC_DISC_13;
    }
}
