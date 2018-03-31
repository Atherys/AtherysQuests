package com.atherys.quests.quest;

import com.atherys.quests.api.quest.AbstractQuest;
import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.requirement.Requirement;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.atherys.quests.views.AnyQuestView;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Quest which seperates it's Objectives out into Stages, so that they must be completed one-by-one, in an ordered fashion. WIP.
 */
public class StagedQuest extends AbstractQuest<StagedQuest> {

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

    @Expose private List<Requirement> requirements = new ArrayList<>();

    @Expose private Stage head;
    @Expose private Stage current;

    @Expose private List<Reward> rewards = new ArrayList<>();

    @Expose
    private boolean started = false;
    @Expose
    private boolean complete = false;

    StagedQuest ( String id, int version ) {
        super ( id, version );
    }

    private StagedQuest ( StagedQuest quest ) {
        super ( quest.getId(), quest.getVersion(), quest.getName(), quest.getDescription() );
        this.requirements = CopyUtils.copyList( requirements );
        this.head = quest.getHead().copy();
        this.current = head;
        this.rewards = CopyUtils.copyList( rewards );
        this.started = quest.isStarted();
        this.complete = quest.isComplete();
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
    public void notify ( Event event, Quester quester ) {
        if ( isComplete() ) return;

        if ( current.getObjective().isComplete() ) {
            if ( this.current.hasNext() ) {
                this.started = true;
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
    public boolean isStarted () {
        return started;
    }

    @Override
    public boolean isComplete () {
        return complete;
    }

    @Override
    public AnyQuestView<StagedQuest> createView () {
        return new AnyQuestView<>( this );
    }

    @Override
    public StagedQuest copy () {
        return new StagedQuest( this );
    }
}
