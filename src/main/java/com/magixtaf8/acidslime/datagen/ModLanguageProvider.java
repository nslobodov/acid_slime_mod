package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.AcidSlime;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, AcidSlime.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // Add your blocks, items, and tooltips here
    }
}
