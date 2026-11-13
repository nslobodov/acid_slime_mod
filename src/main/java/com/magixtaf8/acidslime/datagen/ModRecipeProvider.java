package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.block.ModBlocks;
import com.magixtaf8.acidslime.component.ModDataComponents;
import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ACID_SLIME_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.ACIDSLIMEBALL.get())
                .unlockedBy("has_acid_slimeball", has(ModItems.ACIDSLIMEBALL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ACIDSLIMEBALL.get(), 9)
                .requires(ModBlocks.ACID_SLIME_BLOCK.get())
                .unlockedBy("has_acid_slime_block", has(ModBlocks.ACID_SLIME_BLOCK))
                .save(recipeOutput, "acidslime:acid_slimeball_from_acid_slime_block");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ACID_SLIME_STAIN.get(), 2)
                .pattern("AAA")
                .define('A', ModItems.ACIDSLIMEBALL.get())
                .unlockedBy("has_acid_slimeball", has(ModItems.ACIDSLIMEBALL))
                .save(recipeOutput);
        List<Item> boots = List.of(
                Items.LEATHER_BOOTS,
                Items.CHAINMAIL_BOOTS,
                Items.IRON_BOOTS,
                Items.GOLDEN_BOOTS,
                Items.DIAMOND_BOOTS,
                Items.NETHERITE_BOOTS
        );

        for (Item boot : boots) {
            acidSlimeBootsSmithing(recipeOutput, boot);
        }
    }
    private static void acidSlimeBootsSmithing(RecipeOutput recipeOutput, Item bootItem) {
        ItemStack resultStack = new ItemStack(bootItem);
        resultStack.set(ModDataComponents.ACID_SLIME_UPGRADED.get(), true);

        String bootName = bootItem.builtInRegistryHolder().key().location().getPath();
        ResourceLocation recipeId = ResourceLocation.fromNamespaceAndPath(
                AcidSlime.MODID, bootName + "_acid_slime_smithing"
        );

        // Build the recipe advancement (unlocks in recipe book)
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .addCriterion("has_acid_slime_ball", has(ModItems.ACIDSLIMEBALL.get()))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);

        // Create the SmithingTransformRecipe directly with your ItemStack (including DataComponents!)
        SmithingTransformRecipe recipe = new SmithingTransformRecipe(
                Ingredient.EMPTY, // Template slot (or Ingredient.EMPTY)
                Ingredient.of(bootItem),                                   // Base slot
                Ingredient.of(ModItems.ACIDSLIMEBALL.get()),             // Addition slot
                resultStack                                                // Output ItemStack WITH component
        );

        recipeOutput.accept(recipeId, recipe, advancement.build(recipeId.withPrefix("recipes/combat/")));
    }

}
