package net.throckmorpheus.incursion.util;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemTags {
    public static final TagKey<Item> ENCHANTABLE = TagKey.of(Registry.ITEM_KEY, new Identifier("c","enchantable"));
    public static final TagKey<Item> ENCHANTING_CATALYSTS = TagKey.of(Registry.ITEM_KEY, new Identifier("incursion","enchanting_catalysts"));
    public static final TagKey<Item> LAPIS_LAZULIS = TagKey.of(Registry.ITEM_KEY, new Identifier("c","lapis_lazulis"));
}
