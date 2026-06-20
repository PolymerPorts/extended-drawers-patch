package eu.pb4.extdrawpatch.impl.extensions;

import eu.pb4.extdrawpatch.impl.BlockItemPolymerOverlay;
import eu.pb4.extdrawpatch.impl.OverlayFactoryBlock;
import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import io.github.mattidragon.extendeddrawers.extensions.ExtendedDrawersExtensions;
import io.github.mattidragon.extendeddrawers.extensions.block.ExtensionBlocks;
import io.github.mattidragon.extendeddrawers.extensions.component.ExtensionDataComponents;
import io.github.mattidragon.extendeddrawers.extensions.item.ExtensionItems;
import net.minecraft.resources.Identifier;

public class EDExtensionsPolymerPatch {
    public static void onInitialize() {
        PolymerResourcePackUtils.addModAssets("extended_drawers_extensions");
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.fromNamespaceAndPath("extended_drawers_extensions", "block"));

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