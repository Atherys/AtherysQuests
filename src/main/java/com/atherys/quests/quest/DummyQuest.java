package com.atherys.quests.quest;

import com.atherys.quests.quest.objective.KillEntityObjective;
import com.atherys.quests.quest.objective.ReachLocationObjective;
import com.atherys.quests.quest.reward.SingleItemReward;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Arrays;

public class DummyQuest extends SimpleQuest {

    public DummyQuest () {
        super( "dummyQuest", 1 );
        setName( Text.of( "Dummy Quest" ) );
        setDescription( Text.of( "A dummy quest to prove that SimpleQuest works. Kill some things, reach a point, and get a magical anvil!" ) );
        addObjective( KillEntityObjective.of( "zombie", 4 ) );
        addObjective( KillEntityObjective.of( "creeper", 3 ) );
        addObjective( new ReachLocationObjective( Text.of( "a very high place" ), Vector3d.from( 500, 130, 600 ), 5.0d ) );
        addReward( new SingleItemReward( ItemStack.builder()
                    .itemType( ItemTypes.ANVIL )
                    .quantity( 1 )
                    .add( Keys.DISPLAY_NAME, Text.of( "The Magical Anvil" ) )
                    .add( Keys.ITEM_ENCHANTMENTS, Arrays.asList(
                            Enchantment.of( EnchantmentTypes.FLAME, 10 ),
                            Enchantment.of( EnchantmentTypes.INFINITY, 4 )
                        )
                    )
                    .add( Keys.ITEM_LORE, Arrays.asList(
                            Text.of( "An anvil so magical, it will" ),
                            Text.of( "actually set you on fire." ),
                            Text.of( "For infinity." )
                        )
                    )
                    .build()
                )
        );
    }
}
