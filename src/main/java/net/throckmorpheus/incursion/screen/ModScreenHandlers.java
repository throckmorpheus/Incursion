package net.throckmorpheus.incursion.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.throckmorpheus.incursion.Incursion;

public class ModScreenHandlers {
    public static final ScreenHandlerType<CustomEnchantmentScreenHandler> CUSTOM_ENCHANTMENT_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(Incursion.MOD_ID, "enchanting_table"),
                    CustomEnchantmentScreenHandler::new);
}
