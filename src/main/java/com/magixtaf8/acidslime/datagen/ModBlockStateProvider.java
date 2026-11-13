package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AcidSlime.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        slimeBlockWithItem(ModBlocks.ACID_SLIME_BLOCK);

        // Генерируем блок, состояния и модель предмета для обоих Multiface-блоков
        multifaceBlockWithItem(ModBlocks.ACID_SLIME_STAIN, "acid_slime_stain");
        multifaceBlockWithItem(ModBlocks.ACID_SLIME_SPLASH, "acid_slime_splash");
    }

    private void slimeBlockWithItem(DeferredBlock<?> deferredBlock) {
        String name = deferredBlock.getId().getPath();

        ModelFile slimeModel = models().withExistingParent(name, mcLoc("block/slime_block"))
                .renderType("translucent")
                .texture("texture", modLoc("block/" + name))
                .texture("particle", modLoc("block/" + name));

        simpleBlockWithItem(deferredBlock.get(), slimeModel);
    }

    public void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void multifaceBlockWithItem(DeferredBlock<? extends Block> deferredBlock, String name) {
        // 1. Создаем плоскую 2D-модель с поддержкой прозрачности (cutout)
        ModelFile faceModel = models().withExistingParent("block/" + name, ResourceLocation.withDefaultNamespace("block/block"))
                .renderType("translucent") // Обязательно для прозрачности текстур в NeoForge 1.21.1!
                .texture("particle", modLoc("block/" + name))
                .texture("texture", modLoc("block/" + name))
                .element()
                .from(0.0F, 0.0F, 0.05F)
                .to(16.0F, 16.0F, 0.05F)
                .face(Direction.NORTH)
                .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                .texture("#texture")
                .end()
                .face(Direction.SOUTH)
                .uvs(0.0F, 0.0F, 16.0F, 16.0F)
                .texture("#texture")
                .end()
                .end();

        // 2. Генерируем правила MultiPart для всех 6 сторон куба
        MultiPartBlockStateBuilder builder = getMultipartBuilder(deferredBlock.get());

        builder.part().modelFile(faceModel).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.NORTH), true);

        builder.part().modelFile(faceModel).rotationY(90).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.EAST), true);

        builder.part().modelFile(faceModel).rotationY(180).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.SOUTH), true);

        builder.part().modelFile(faceModel).rotationY(270).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.WEST), true);

        builder.part().modelFile(faceModel).rotationX(90).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.DOWN), true);

        builder.part().modelFile(faceModel).rotationX(270).addModel()
                .condition(MultifaceBlock.getFaceProperty(Direction.UP), true);

        // 3. Генерируем плоскую модель предмета для инвентаря
        itemModels().withExistingParent(name, ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }
}
