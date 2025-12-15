package eu.pb4.extdrawpatch.impl.ui;

import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import io.github.mattidragon.extendeddrawers.component.LimiterLimitComponent;
import io.github.mattidragon.extendeddrawers.registry.ModDataComponents;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSets;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.SequencedSet;

public class CapacityLimiterGui extends AnvilInputGui {
    private final InteractionHand hand;
    private final ItemStack stack;
    private static final Component TEXTURE = Component.literal("-0.").setStyle(Style.EMPTY.withColor(0xFFFFFF).withFont(new FontDescription.Resource(Identifier.parse("extdraw-patch:gui"))));
    private static final ItemStack EMPTY = ItemDisplayElementUtil.getModel(Identifier.parse("extdraw-patch:sgui/empty"));
    private static final ItemStack CLOSE = ItemDisplayElementUtil.getModel(Identifier.parse("extdraw-patch:sgui/close"));
    private static final ItemStack DONE = ItemDisplayElementUtil.getModel(Identifier.parse("extdraw-patch:sgui/done"));
    private static final ItemStack DONE_BLOCKED = ItemDisplayElementUtil.getModel(Identifier.parse("extdraw-patch:sgui/done_blocked"));

    private long value = Long.MAX_VALUE;

    public CapacityLimiterGui(ServerPlayer player, ItemStack stack, InteractionHand hand) {
        super(player, false);
        this.stack = stack;
        this.hand = hand;
        this.value = stack.getOrDefault(ModDataComponents.LIMITER_LIMIT, LimiterLimitComponent.NO_LIMIT).limit();
        this.setTitle(Component.empty().append(TEXTURE).append(stack.getHoverName()));
        var val = value != Long.MAX_VALUE ? String.valueOf(this.value) : "";
        this.setDefaultInputValue(val);
        this.updateDoneButton(value != Long.MAX_VALUE);
        this.updateDefault(val);
        var close = GuiElementBuilder.from(CLOSE);
        close.setName(CommonComponents.GUI_BACK);
        close.setCallback(x -> {
            player.connection.send(new ClientboundSoundEntityPacket(
                    SoundEvents.UI_BUTTON_CLICK, SoundSource.MASTER, player,0.5f, 1, 0
            ));
            this.close();
        });
        this.setSlot(2, close);
        this.open();
    }

    public static void register() {
    }

    private void updateDefault(String current) {
        ItemStack itemStack = EMPTY.copy();
        itemStack.set(DataComponents.CUSTOM_NAME, Component.literal(current));
        itemStack.set(DataComponents.ITEM_NAME, Component.literal(""));
        itemStack.set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(true, ReferenceSortedSets.emptySet()));
        this.setSlot(0, itemStack, (a, b, c, d) -> {
            this.screenHandler.setRemoteSlot(2, ItemStack.EMPTY);
        });
    }

    private void updateDoneButton(boolean success) {
        if (!success) {
            var b = GuiElementBuilder.from(DONE_BLOCKED);
            b.setName(Component.empty().append(CommonComponents.GUI_DONE).withStyle(ChatFormatting.GRAY));
            this.setSlot(1, b);
            return;
        }

        var b = GuiElementBuilder.from(DONE);
        b.setName(CommonComponents.GUI_DONE);
        b.setCallback(x -> {
            player.connection.send(new ClientboundSoundEntityPacket(
                    SoundEvents.UI_BUTTON_CLICK, SoundSource.MASTER, player,0.5f, 1, 0
            ));
            this.stack.set(ModDataComponents.LIMITER_LIMIT, new LimiterLimitComponent(this.value));
            this.close();
        });
        this.setSlot(1, b);
    }


    @Override
    public void onInput(String input) {
        super.onInput(input);
        updateDefault(input);
        try {
            this.value = Long.parseLong(input);
            this.updateDoneButton(true);
        } catch (Throwable ignored) {
            this.updateDoneButton(false);
        }
        this.screenHandler.setRemoteSlot(2, ItemStack.EMPTY);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.player.getItemInHand(this.hand) != this.stack) {
            this.close();
        }
    }
}
