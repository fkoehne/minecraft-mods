package com.example.funnyrecipes.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// remap=false: 26.1.2 ships without obfuscation (native Mojang names).
// The intermediary layer doesn't exist for this version, so we target the
// class and method directly by their Mojang names at runtime.
@Mixin(targets = "net.minecraft.world.inventory.ResultSlot", remap = false)
public class CraftingResultSlotMixin {

    private static List<Item> ITEM_POOL;

    private static List<Item> getItemPool() {
        if (ITEM_POOL == null) {
            ITEM_POOL = new ArrayList<>();
            for (Item item : BuiltInRegistries.ITEM) {
                if (item != Items.AIR) {
                    ITEM_POOL.add(item);
                }
            }
        }
        return ITEM_POOL;
    }

    @Inject(method = "onTake", remap = false, at = @At("HEAD"))
    private void randomizeCraftingResult(Player player, ItemStack stack, CallbackInfo ci) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            List<Item> pool = getItemPool();
            Item randomItem = pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
            int count = stack.getCount();
            stack.setCount(0);
            player.addItem(new ItemStack(randomItem, count));
        }
    }
}
