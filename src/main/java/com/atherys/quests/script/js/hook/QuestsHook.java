package com.atherys.quests.script.js.hook;

import com.atherys.quests.api.quest.Quest;
import com.atherys.quests.managers.QuestManager;
import com.atherys.quests.quest.objective.Objectives;
import com.atherys.quests.quest.requirement.Requirements;
import com.atherys.quests.quest.reward.Rewards;

import javax.script.ScriptContext;

public class QuestsHook implements ScriptHook {

    @Override
    public void registerHooks(ScriptContext context) {
        context.setAttribute("Requirements", new Requirements(), ScriptContext.GLOBAL_SCOPE);
        context.setAttribute("Objectives", new Objectives(), ScriptContext.GLOBAL_SCOPE);
        context.setAttribute("Rewards", new Rewards(), ScriptContext.GLOBAL_SCOPE);
    }

    public Quest getQuestFromId(String id) {
        return QuestManager.getInstance().getQuest(id).orElse(null);
    }

}
