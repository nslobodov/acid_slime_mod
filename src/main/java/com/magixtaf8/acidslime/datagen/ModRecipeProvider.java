package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.block.ModBlocks;
import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ACID_SLIME_SMEAR.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.ACIDSLIMEBALL.get())
                .unlockedBy("has_acid_slimeball", has(ModItems.ACIDSLIMEBALL)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ACIDSLIMEBALL.get(), 9)
                .requires(ModBlocks.ACID_SLIME_SMEAR.get())
                .unlockedBy("has_acid_slime_smear", has(ModBlocks.ACID_SLIME_SMEAR))
                .save(recipeOutput, "acidslime:acid_slimeball_from_acid_slime_smear");
    }
}
