package net.throckmorpheus.incursion.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.throckmorpheus.incursion.util.ModItemTags;

public class LapisSlot extends Slot {
    public LapisSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isIn(ModItemTags.LAPIS_LAZULIS);
    }
}
