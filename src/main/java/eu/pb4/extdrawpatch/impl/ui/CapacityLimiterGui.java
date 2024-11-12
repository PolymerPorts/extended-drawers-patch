package eu.pb4.extdrawpatch.impl.ui;

import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.AnvilInputGui;
import io.github.mattidragon.extendeddrawers.registry.ModDataComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

public class CapacityLimiterGui extends AnvilInputGui {
    private final Hand hand;
    private final ItemStack stack;
    private static final Text TEXTURE = Text.literal("-0.").setStyle(Style.EMPTY.withColor(0xFFFFFF).withFont(Identifier.of("extdraw-patch:gui")));
    private static final ItemStack EMPTY = ItemDisplayElementUtil.getModel(Identifier.of("extdraw-patch:sgui/empty"));
    private static final ItemStack CLOSE = ItemDisplayElementUtil.getModel(Identifier.of("extdraw-patch:sgui/close"));
    private static final ItemStack DONE = ItemDisplayElementUtil.getModel(Identifier.of("extdraw-patch:sgui/done"));
    private static final ItemStack DONE_BLOCKED = ItemDisplayElementUtil.getModel(Identifier.of("extdraw-patch:sgui/done_blocked"));

    private long value = -1;

    public CapacityLimiterGui(ServerPlayerEntity player, ItemStack stack, Hand hand) {
        super(player, false);
        this.stack = stack;
        this.hand = hand;
        this.value = stack.getOrDefault(ModDataComponents.LIMITER_LIMIT, -1L);
        this.setTitle(Text.empty().append(TEXTURE).append(stack.getName()));
        var val = value >= 0 ? String.valueOf(this.value) : "";
        this.setDefaultInputValue(val);
        this.updateDoneButton(value >= 0);
        this.updateDefault(val);
        var close = GuiElementBuilder.from(CLOSE);
        close.setName(ScreenTexts.BACK);
        close.setCallback(x -> {
            player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 0.5f, 1);
            this.close();
        });
        this.setSlot(2, close);
        this.open();
    }

    public static void register() {
    }

    private void updateDefault(String current) {
        ItemStack itemStack = EMPTY.copy();
        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(current));
        itemStack.set(DataComponentTypes.ITEM_NAME, Text.literal(""));
        itemStack.set(DataComponentTypes.HIDE_TOOLTIP, Unit.INSTANCE);
        this.setSlot(0, itemStack, (a, b, c, d) -> {
            this.screenHandler.setPreviousTrackedSlot(2, ItemStack.EMPTY);
        });
    }

    private void updateDoneButton(boolean success) {
        if (!success) {
            var b = GuiElementBuilder.from(DONE_BLOCKED);
            b.setName(Text.empty().append(ScreenTexts.DONE).formatted(Formatting.GRAY));
            this.setSlot(1, b);
            return;
        }

        var b = GuiElementBuilder.from(DONE);
        b.setName(ScreenTexts.DONE);
        b.setCallback(x -> {
            player.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.MASTER, 0.5f, 1);
            this.stack.set(ModDataComponents.LIMITER_LIMIT, this.value);
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
        this.screenHandler.setPreviousTrackedSlot(2, ItemStack.EMPTY);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.player.getStackInHand(this.hand) != this.stack) {
            this.close();
        }
    }
}
