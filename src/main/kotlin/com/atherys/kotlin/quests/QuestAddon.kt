package com.atherys.kotlin.quests

import com.atherys.quests.events.DialogRegistrationEvent
import com.atherys.quests.events.QuestRegistrationEvent
import com.atherys.quests.util.GsonUtils
import org.spongepowered.api.event.Listener
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin

@Plugin(
        id = "questaddon",
        name = "AtherysQuests Addon",
        description = "A plugin containing quests for the AtherysQuests plugin",
        version = "1.0.0",
        dependencies = [ Dependency( id = "atherysquests" ) ]
)
class QuestAddon {

    val simpleDummyQuest: DummyQuest.Simple = DummyQuest.Simple()
    val stagedDummyQuest: DummyQuest.Staged = DummyQuest.Staged()

    @Listener
    fun onQuestRegistration( event: QuestRegistrationEvent) {
        GsonUtils.getQuestRuntimeTypeAdapterFactory().registerSubtype( simpleDummyQuest.javaClass )
        GsonUtils.getQuestRuntimeTypeAdapterFactory().registerSubtype( stagedDummyQuest.javaClass )

        event.register( simpleDummyQuest )
    }

    @Listener
    fun onDialogRegistration( event: DialogRegistrationEvent ) {
        event.register( DummyQuest.dialog( simpleDummyQuest ) )
    }

}