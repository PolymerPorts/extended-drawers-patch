package eu.pb4.extdrawpatch.impl;

import eu.pb4.extdrawpatch.impl.extensions.EDExtensionsPolymerPatch;
import eu.pb4.extdrawpatch.impl.ui.CapacityLimiterGui;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import io.github.mattidragon.extendeddrawers.registry.ModBlocks;
import io.github.mattidragon.extendeddrawers.registry.ModDataComponents;
import io.github.mattidragon.extendeddrawers.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedDrawersPolymerPatch implements ModInitializer {
    public static final String MOD_ID = "extended-drawers-polymer-patch";
    public static final Logger LOGGER = LoggerFactory.getLogger("extended-drawers-polymer-patch");
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets("extended_drawers");
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.fromNamespaceAndPath("extdraw-patch", "sgui"));

        CapacityLimiterGui.register();

        PolymerComponent.registerDataComponent(ModDataComponents.COMPACTING_DRAWER_CONTENTS, ModDataComponents.DRAWER_CONTENTS, ModDataComponents.LIMITER_LIMIT);
        PolymerBlockUtils.registerBlockEntity(ModBlocks.COMPACTING_DRAWER_BLOCK_ENTITY, ModBlocks.DRAWER_BLOCK_ENTITY, ModBlocks.SHADOW_DRAWER_BLOCK_ENTITY);
        RegistrySyncUtils.setServerEntry(BuiltInRegistries.RECIPE_SERIALIZER, ModRecipes.COPY_LIMITER_SERIALIZER);

        ExtendedDrawers.SHIFT_ACCESS = () -> true;

        if (FabricLoader.getInstance().isModLoaded("extended_drawers_extensions")) {
            EDExtensionsPolymerPatch.onInitialize();
        }
    }
}