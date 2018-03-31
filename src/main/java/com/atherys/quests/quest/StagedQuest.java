package com.atherys.quests.quest;

import com.atherys.quests.base.Observer;
import com.atherys.quests.base.Prototype;
import com.atherys.quests.quest.objective.Objective;
import com.atherys.quests.quest.requirement.Requirement;
import com.atherys.quests.quest.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.views.StagedQuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Quest which seperates it's Objectives out into Stages, so that they must be completed one-by-one, in an ordered fashion. WIP.
 */
public class StagedQuest implements Quest<StagedQuest, StagedQuestView> {

    public static class Stage implements Observer<Event>, Prototype<Stage>, Iterable<Stage> {

        @Expose private Objective objective;
        @Expose private List<Reward> rewards;
        @Expose @Nullable private Stage next;

        public Stage ( Objective objective, List<Reward> rewards, @Nullable Stage next ) {
            this.objective = objective;
            this.rewards = rewards;
            if ( next != null ) this.next = next;
        }

        private Stage ( Stage stage ) {
            this.objective = CopyUtils.copy( stage.getObjective() );
            this.rewards = CopyUtils.copyList( stage.getRewards() );
            if ( stage.getNext() != null ) this.next = new Stage( stage.getNext() );
        }

        public Objective getObjective() {
            return objective;
        }

        public List<Reward> getRewards () {
            return rewards;
        }

        public boolean hasNext() {
            return next != null;
        }

        @Nullable
        public Stage getNext() {
            return next;
        }

        @Override
        public void notify ( Event event, Quester quester ) {
            objective.notify( event, quester );
        }

        public void award ( Quester quester ) {
            rewards.forEach( reward -> reward.award( quester ) );
        }

        @Override
        public Stage copy () {
            return new Stage( this );
        }

        @Override
        public Iterator<Stage> iterator () {
            return new Iterator<Stage>() {
                @Override
                public boolean hasNext () {
                    return Stage.this.next != null;
                }

                @Override
                public Stage next () {
                    return Stage.this.next;
                }
            };
        }
    }

    @Expose private String id;
    @Expose private Text name = Text.of( "An unnamed Staged Quest." );
    @Expose private Text description = Text.of( "A description of a Staged Quest." );
    @Expose private int version;

    @Expose private List<Requirement> requirements = new ArrayList<>();

    @Expose private Stage head;
    @Expose private Stage current;

    @Expose private List<Reward> rewards = new ArrayList<>();

    @Expose
    private boolean started = false;
    @Expose
    private boolean complete = false;

    StagedQuest ( String id, int version ) {
        this.id = id;
        this.version = version;
    }

    private StagedQuest ( StagedQuest quest ) {
        this.id = quest.getId();
        this.name = quest.getName();
        this.description = quest.getDescription();
        this.version = quest.getVersion();
        this.requirements = CopyUtils.copyList( requirements );
        this.head = quest.getHead().copy();
        this.current = head;
        this.rewards = CopyUtils.copyList( rewards );
    }

    @Override
    public String getId () {
        return id;
    }

    @Override
    public Text getName () {
        return name;
    }

    @Override
    public Text getDescription () {
        return description;
    }

    @Override
    public List<Requirement> getRequirements () {
        return requirements;
    }

    public Stage getHead() {
        return head;
    }

    @Override
    public List<Objective> getObjectives () {
        List<Objective> objectives = new ArrayList<>();
        head.forEach( stage -> objectives.add( stage.getObjective() ) );
        return objectives;
    }

    @Override
    public List<Reward> getRewards () {
        return rewards;
    }

    @Override
    public boolean meetsRequirements ( Quester quester ) {
        for ( Requirement requirement : requirements ) {
            if ( !requirement.check( quester ) ) return false;
        }
        return true;
    }

    @Override
    public void notify ( Event event, Quester quester ) {
        if ( isComplete() ) return;

        if ( current.getObjective().isComplete() ) {
            if ( this.current.hasNext() ) {
                current.award( quester );
                this.current = current.getNext();
                QuestMsg.info( quester, "You have completed an objective for the quest \"", this.getName(), "\"" );
            } else {
                this.complete = true;
                return;
            }
        }

        current.notify( event, quester );
    }

    @Override
    public void award ( Quester quester ) {
        rewards.forEach( reward -> reward.award( quester ) );
    }

    @Override
    public boolean isStarted () {
        return started;
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public int getVersion () {
        return version;
    }

    @Override
    public StagedQuestView createView () {
        return new StagedQuestView( this );
    }

    @Override
    public StagedQuest copy () {
        return new StagedQuest( this );
    }
}
