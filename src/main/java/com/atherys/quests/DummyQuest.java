package com.atherys.quests;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.dialog.tree.DialogNode;
import com.atherys.quests.dialog.tree.DialogTree;
import com.atherys.quests.quest.SimpleQuest;
import com.atherys.quests.quest.Stage;
import com.atherys.quests.quest.StagedQuest;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.quests.quest.reward.Rewards;
import com.atherys.quests.quester.Quester;
import com.flowpowered.math.vector.Vector3d;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DummyQuest {

    public static DialogTree dialog( Quest quest ) {

        DialogNode root = DialogNode.builder( 0 )
                .npc( Text.of( "Hello, weary traveller!" ) )
                .responses(
                        DialogNode.builder( 1 )
                                .player( Text.of( "Hello, Merchant! Have you any work for me today?" ) )
                                .npc(
                                        Text.of( "You know, I ", TextStyles.ITALIC, " may", TextStyles.RESET ," actually have something you'd be", TextColors.DARK_BLUE, " interested in." ),
                                        Text.of( "Recently, the outskirts of the town have been getting ravaged by some very nasty creatures." ),
                                        Text.of( "I've heard that the King himself wishes this situation to be dealt with swiftly. There may even be a reward for the one who does it." )
                                )
                                .responses(
                                        DialogNode.builder( 10 )
                                                .player( Text.of( "Well, sign me up! I love me a bit of danger." ) )
                                                .npc( Text.of( "Oh, excellent! Just remember who tipped you off, when the time comes for the King to reward you, eh? ;)" ) )
                                                .quest( quest )
                                                .build(),
                                        DialogNode.builder( 11 )
                                                .player( Text.of( "Oh, no, I'm not in the murdering vibe today. Perhaps another day." ) )
                                                .npc( Text.of( "Well, I wouldn't take too long if I were you. Someone else is bound to pick up on that reward." ) )
                                                .build()
                                )
                                .build(),
                        DialogNode.builder( 2 )
                                .player( Text.of( "I have no time for chit-chat. Goodbye." ) )
                                .build()
                )
                .build();

        return DialogTree.builder( "merchantDialog" ).root( root ).build();

    }

    public static class Simple extends SimpleQuest {
        public Simple() {
            super( "dummyQuest", 1 );

            setName( Text.of( "Dummy Quest" ) );

            setDescription( Text.of( "A dummy quest to prove that SimpleQuest works. Kill some things, reach a point, and get a magical anvil!" ) );

            addObjective( Objectives.killEntity( "zombie", 4 ) );

            addObjective( Objectives.killEntity( "creeper", 3 ) );

            Optional<World> world = Sponge.getServer().getWorld( Sponge.getServer().getDefaultWorldName() );

            addObjective( Objectives.reachLocation( Text.of( "a very high place" ), new Location<>( world.get(), Vector3d.from( 500, 130, 600 ) ), 5.0d ) );

            addReward( Rewards.item( ItemStack.builder()
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

        @Override
        public void pickUp( Quester quester ) {
            Player player = quester.getCachedPlayer();
            if ( player == null ) return;

            List<Entity> zombies = new ArrayList<>();
            List<Entity> creepers = new ArrayList<>();

            for ( int i = 0; i < 5; i++ ) {
                zombies.add(
                        quester.getCachedPlayer().getWorld().createEntity(
                                EntityTypes.ZOMBIE,
                                player.getLocation().getPosition().add(
                                        RandomUtils.nextFloat( 0.0f, 15.0f ),
                                        0,
                                        RandomUtils.nextFloat( 0.0f, 15.0f )
                                )
                        )
                );
            }

            for ( int i = 0; i < 4; i++ ) {
                creepers.add(
                        quester.getCachedPlayer().getWorld().createEntity(
                                EntityTypes.CREEPER,
                                player.getLocation().getPosition().add(
                                        RandomUtils.nextFloat( 0.0f, 15.0f ),
                                        0,
                                        RandomUtils.nextFloat( 0.0f, 15.0f )
                                )
                        )
                );
            }

            player.getWorld().spawnEntities( zombies );
            player.getWorld().spawnEntities( creepers );
        }
    }

    public static class Staged extends StagedQuest {
        public Staged() {
            super( "dummyQuestStaged", 1 );

            setName( Text.of( "Staged Dummy Quest" ) );

            setDescription( Text.of( "The staged version of the dummy quest. Kill some things, one type at a time, and then reach a point. Also, you still get a magical anvil." ) );

            addRequirement( Requirements.level( 12 ) );

            addStage( new Stage( Objectives.killEntity( "zombie", 4 ) ) );

            addStage( new Stage( Objectives.killEntity( "creeper", 3 ) ) );

            Optional<World> world = Sponge.getServer().getWorld( Sponge.getServer().getDefaultWorldName() );

            addStage( new Stage( Objectives.reachLocation( Text.of( "a very high place" ), new Location<>( world.get(), Vector3d.from( 500, 130, 600 ) ), 5.0d ) ) );

            addReward( Rewards.item( ItemStack.builder()
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

        @Override
        public void pickUp( Quester quester ) {
            Player player = quester.getCachedPlayer();
            if ( player == null ) return;

            List<Entity> zombies = new ArrayList<>();
            List<Entity> creepers = new ArrayList<>();

            for ( int i = 0; i < 5; i++ ) {
                zombies.add(
                        quester.getCachedPlayer().getWorld().createEntity(
                                EntityTypes.ZOMBIE,
                                player.getLocation().getPosition().add(
                                        RandomUtils.nextFloat( 0.0f, 15.0f ),
                                        0,
                                        RandomUtils.nextFloat( 0.0f, 15.0f )
                                )
                        )
                );
            }

            for ( int i = 0; i < 4; i++ ) {
                creepers.add(
                        quester.getCachedPlayer().getWorld().createEntity(
                                EntityTypes.CREEPER,
                                player.getLocation().getPosition().add(
                                        RandomUtils.nextFloat( 0.0f, 15.0f ),
                                        0,
                                        RandomUtils.nextFloat( 0.0f, 15.0f )
                                )
                        )
                );
            }

            player.getWorld().spawnEntities( zombies );
            player.getWorld().spawnEntities( creepers );
        }
    }
}
