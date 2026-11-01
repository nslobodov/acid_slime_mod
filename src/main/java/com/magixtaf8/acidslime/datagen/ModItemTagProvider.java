package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.item.ModItems;
import com.magixtaf8.acidslime.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AcidSlime.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.ACID_ITEM)
                .add(ModItems.ACID_SLIME_SPAWN_EGG.get())
                .add(ModItems.ACIDSLIMEBALL.get());
    }
}
