package eu.pb4.extdrawpatch.mixin.mod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import io.github.mattidragon.extendeddrawers.item.DrawerItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.nucleoid.packettweaker.PacketContext;

@Mixin(DrawerItem.class)
public class DrawerItemMixin extends BlockItem implements PolymerItem {
    public DrawerItemMixin(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var x = super.useOn(context);
        if (x == InteractionResult.SUCCESS) {
            if (context.getPlayer() instanceof ServerPlayer player) {
                var pos = Vec3.atCenterOf(context.getClickedPos().relative(context.getClickedFace()));
                var blockSoundGroup = this.getBlock().defaultBlockState().getSoundType();
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(this.getPlaceSound(this.getBlock().defaultBlockState())), SoundSource.BLOCKS, pos.x, pos.y, pos.z, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F, context.getPlayer().getRandom().nextLong()));
            }
            return InteractionResult.SUCCESS_SERVER;
        }
        return x;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.MUSIC_DISC_13;
    }
}
