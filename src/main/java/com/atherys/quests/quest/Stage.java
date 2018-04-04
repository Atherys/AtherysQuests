package com.atherys.quests.quest;

import com.atherys.quests.api.base.Observer;
import com.atherys.quests.api.base.Prototype;
import com.atherys.quests.api.objective.Objective;
import com.atherys.quests.api.reward.Reward;
import com.atherys.quests.quester.Quester;
import com.atherys.quests.util.CopyUtils;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Stage implements Observer<Event>, Prototype<Stage> {

    @Expose private Objective objective;
    @Expose private List<Reward> rewards = new ArrayList<>();
    @Expose @Nullable private Stage next;

    public Stage ( Objective objective ) {
        this ( objective, null, null );
    }

    public Stage ( Objective objective, List<Reward> rewards ) {
        this ( objective, rewards, null );
    }

    public Stage ( Objective objective, @Nullable List<Reward> rewards, @Nullable Stage next ) {
        this.objective = objective;
        if ( rewards != null ) this.rewards = rewards;
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

    protected void setNext ( Stage next ) {
        this.next = next;
    }

    @Override
    public Stage copy () {
        return new Stage( this );
    }
}
