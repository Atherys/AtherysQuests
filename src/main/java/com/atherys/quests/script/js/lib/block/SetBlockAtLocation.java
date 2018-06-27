package com.atherys.quests.script.js.lib.block;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.block.trait.BooleanTraits;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.function.BiFunction;

public class SetBlockAtLocation implements BiFunction<Location<World>, BlockState, Boolean> {
    @Override
    public Boolean apply(Location<World> worldLocation, BlockState state) {
        return worldLocation.setBlock(state);
    }
}
