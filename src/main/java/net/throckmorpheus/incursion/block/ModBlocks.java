package net.throckmorpheus.incursion.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.throckmorpheus.incursion.Incursion;

public class ModBlocks {
    private static final Block CHISELED_PURPUR = registerBlock("chiseled_purpur",
            new Block(FabricBlockSettings.copy(Blocks.PURPUR_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    private static final Block CUT_PURPUR = registerBlock("cut_purpur",
            new Block(FabricBlockSettings.copy(Blocks.PURPUR_BLOCK)), ItemGroup.BUILDING_BLOCKS);
    private static final Block CUT_PURPUR_SLAB = registerBlock("cut_purpur_slab",
            new SlabBlock(FabricBlockSettings.copy(CUT_PURPUR)), ItemGroup.BUILDING_BLOCKS);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Incursion.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(Incursion.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        Incursion.LOGGER.info("Initialising blocks for " + Incursion.MOD_ID);
    }
}
