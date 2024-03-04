package de.kidinthedark.bhwmcplugin.recipes;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.jetbrains.annotations.NotNull;

public class LightRecipe {

    private final ItemStack item = new ItemStack(Material.LIGHT);
    private final NamespacedKey key = new NamespacedKey(BHWMcPlugin.inst, "light");

    public ShapedRecipe getRecipe() {
        item.setAmount(8);

        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TTT", "TGT", "TTT");

        recipe.setIngredient('T', Material.TORCH);
        recipe.setIngredient('G', Material.GLASS);

        recipe.setCategory(CraftingBookCategory.BUILDING);

        return recipe;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
