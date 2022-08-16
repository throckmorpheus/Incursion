package net.throckmorpheus.incursion.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.throckmorpheus.incursion.screen.slot.EnchantableItemSlot;
import net.throckmorpheus.incursion.screen.slot.EnchantingCatalystSlot;
import net.throckmorpheus.incursion.screen.slot.LapisSlot;

public class CustomEnchantmentScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private Slot enchantSlot;

    public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory,new SimpleInventory(4));
    }

    public CustomEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.CUSTOM_ENCHANTMENT_SCREEN_HANDLER,syncId);
        checkSize(inventory,4);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        addTableSlots(inventory);
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    private void addTableSlots(Inventory inventory) {
        enchantSlot = this.addSlot(new EnchantableItemSlot(inventory,0,27,15)); //Item being enchanted
        this.addSlot(new EnchantingCatalystSlot(inventory,1,8,46)); //Left catalyst
        this.addSlot(new EnchantingCatalystSlot(inventory,2,46,46)); //Right catalyst
        this.addSlot(new LapisSlot(inventory,3,27,53)); //Fuel
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        //Indices 9 through 35 of PlayerInventory
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        //Indices 0 through 8 of PlayerInventory
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public ItemStack getItemToBeEnchanted () {
        if (!(enchantSlot == null)) {
            return enchantSlot.getStack();
        }
        return ItemStack.EMPTY;
    }
}
