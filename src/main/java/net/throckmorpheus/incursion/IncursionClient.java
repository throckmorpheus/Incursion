package net.throckmorpheus.incursion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.throckmorpheus.incursion.block.entity.ModBlockEntityRenderers;
import net.throckmorpheus.incursion.screen.CustomEnchantmentScreen;
import net.throckmorpheus.incursion.screen.ModScreenHandlers;

@Environment(EnvType.CLIENT)
public class IncursionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ModScreenHandlers.CUSTOM_ENCHANTMENT_SCREEN_HANDLER, CustomEnchantmentScreen::new);
        ModBlockEntityRenderers.registerModBlockEntityRenderers();
    }
}
