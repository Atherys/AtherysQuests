package com.atherys.quests.script.js.hook;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.script.ScriptContext;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;

public class SpongeHook implements ScriptHook {

    @Override
    public void registerHooks(ScriptContext context) {
    }

    public ItemStack createItem(String itemTypeId, int amount) {
        Optional<ItemType> type = Sponge.getRegistry().getType(ItemType.class, itemTypeId);
        return type.map(itemType ->
                ItemStack.builder()
                        .itemType(itemType)
                        .quantity(amount)
                        .build()
        ).orElse(null);
    }

    public void setItemDisplayName(ItemStack item, String name) {
        item.offer(Keys.DISPLAY_NAME, TextSerializers.FORMATTING_CODE.deserialize(name));
    }

    public void setItemLore(ItemStack item, String... lore) {
        Text[] lines = new Text[lore.length];

        for (int i = 0; i < lore.length; i++) {
            lines[i] = TextSerializers.FORMATTING_CODE.deserialize(lore[i]);
        }

        item.offer(Keys.ITEM_LORE, Arrays.asList(lines));
    }

    public void addItemEnchantments(ItemStack itemStack, Enchantment... enchantments) {
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, Arrays.asList(enchantments));
    }

    public void hideItemEnchantments(ItemStack itemStack) {
        itemStack.offer(Keys.HIDE_ENCHANTMENTS, true);
    }

    public Enchantment enchantment(String enchantmentId, int level) {
        Optional<EnchantmentType> type = Sponge.getRegistry().getType(EnchantmentType.class, enchantmentId);
        return type.map(enchantmentType ->
                Enchantment.builder()
                        .type(enchantmentType)
                        .level(level)
                        .build()
        ).orElse(null);
    }

    public Currency getCurrencyById(String id) {
        return Sponge.getRegistry().getType(Currency.class, id).orElse(null);
    }

}
