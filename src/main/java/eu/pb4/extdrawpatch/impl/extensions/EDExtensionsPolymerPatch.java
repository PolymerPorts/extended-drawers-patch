package eu.pb4.extdrawpatch.impl.extensions;

import eu.pb4.extdrawpatch.impl.BaseFactoryBlock;
import eu.pb4.extdrawpatch.impl.BlockItemPolymerOverlay;
import eu.pb4.extdrawpatch.impl.OverlayFactoryBlock;
import eu.pb4.extdrawpatch.impl.ui.CapacityLimiterGui;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.rsm.api.RegistrySyncUtils;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import io.github.mattidragon.extendeddrawers.extensions.ExtendedDrawersExtensions;
import io.github.mattidragon.extendeddrawers.extensions.registry.ExtensionBlocks;
import io.github.mattidragon.extendeddrawers.extensions.registry.ExtensionDataComponents;
import io.github.mattidragon.extendeddrawers.extensions.registry.ExtensionItems;
import io.github.mattidragon.extendeddrawers.registry.ModBlocks;
import io.github.mattidragon.extendeddrawers.registry.ModDataComponents;
import io.github.mattidragon.extendeddrawers.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EDExtensionsPolymerPatch  {
    public static void onInitialize() {
        PolymerResourcePackUtils.addModAssets("extended_drawers_extensions");
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.of("extended_drawers_extensions", "block"));

        BlockStateModelManager.addBlock(ExtendedDrawersExtensions.id("drawer_barrel"), ExtensionBlocks.DRAWER_BARREL);
        BlockStateModelManager.addBlock(ExtendedDrawersExtensions.id("ender_connector"), ExtensionBlocks.ENDER_CONNECTOR);

        PolymerComponent.registerDataComponent(ExtensionDataComponents.LINKING_ENDER_CONNECTOR);
        PolymerBlockUtils.registerBlockEntity(ExtensionBlocks.DRAWER_BARREL_ENTITY, ExtensionBlocks.ENDER_CONNECTOR_ENTITY);

        PolymerBlockUtils.registerOverlay(ExtensionBlocks.DRAWER_BARREL, OverlayFactoryBlock.INSTANCE);
        PolymerBlockUtils.registerOverlay(ExtensionBlocks.ENDER_CONNECTOR, OverlayFactoryBlock.INSTANCE);

        BlockWithElementHolder.registerOverlay(ExtensionBlocks.DRAWER_BARREL, OverlayFactoryBlock.INSTANCE);
        BlockWithElementHolder.registerOverlay(ExtensionBlocks.ENDER_CONNECTOR, OverlayFactoryBlock.INSTANCE);

        PolymerItem.registerOverlay(ExtensionItems.DRAWER_BARREL, new BlockItemPolymerOverlay());
        PolymerItem.registerOverlay(ExtensionItems.ENDER_CONNECTOR, new BlockItemPolymerOverlay());
        PolymerItem.registerOverlay(ExtensionItems.ENDER_CONNECTOR_LINKER, new BlockItemPolymerOverlay());
    }
}