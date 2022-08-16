package net.throckmorpheus.incursion.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.throckmorpheus.incursion.Incursion;

public class ModBlockEntities {
    public static BlockEntityType<CustomEnchantingTableBlockEntity> ENCHANTING_TABLE;

    public static void registerAllBlockEntities() {
        ENCHANTING_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Incursion.MOD_ID,"enchanting_table"), FabricBlockEntityTypeBuilder.create(
                        CustomEnchantingTableBlockEntity::new, Blocks.ENCHANTING_TABLE).build(null));
    }
}
