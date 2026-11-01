package com.magixtaf8.acidslime.item.custom;

import com.magixtaf8.acidslime.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;


public class SuperItem extends Item {
    private static final Map<Block, Block> CHANGE_MAP =
            Map.of(
                    Blocks.GRASS_BLOCK, Blocks.DARK_OAK_LEAVES,
                    Blocks.DARK_OAK_LOG, Blocks.DIRT,
                    Blocks.STONE, Blocks.GOLD_BLOCK
            );
    public SuperItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        if (CHANGE_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), CHANGE_MAP.get(clickedBlock).defaultBlockState());
                context.getItemInHand().hurtAndBreak(2, ((ServerLevel) level), context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
                level.playSound(null, context.getClickedPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS);
            }
        }
        return InteractionResult.SUCCESS;
    }
}