package net.throckmorpheus.incursion.block.entity;

import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class ModBlockEntityRenderers {
    public static void registerModBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(ModBlockEntities.ENCHANTING_TABLE, CustomEnchantingTableBlockEntityRenderer::new);
    }
}
