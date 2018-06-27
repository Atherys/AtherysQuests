package com.atherys.quests.script.js.lib.block;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;

import java.util.function.Function;

public class BlockOf implements Function<String, BlockState> {
    @Override
    public BlockState apply(String blockTypeId) {
        return Sponge.getRegistry().getType(BlockType.class, blockTypeId).map(type -> BlockState.builder().blockType(type).build()).orElse(null);
    }
}
