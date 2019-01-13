package com.atherys.quests;

import com.atherys.quests.api.script.DialogScriptService;
import com.atherys.quests.api.script.QuestScriptService;
import com.atherys.quests.facade.DialogFacade;
import com.atherys.quests.facade.QuestFacade;
import com.atherys.quests.facade.QuesterFacade;
import com.atherys.quests.gson.AtherysQuestsRegistry;
import com.atherys.quests.listener.EntityListener;
import com.atherys.quests.listener.GsonListener;
import com.atherys.quests.listener.InventoryListener;
import com.atherys.quests.listener.MasterEventListener;
import com.atherys.quests.persistence.QuestLocationRepository;
import com.atherys.quests.persistence.QuesterRepository;
import com.atherys.quests.script.SimpleDialogScriptService;
import com.atherys.quests.script.SimpleQuestScriptService;
import com.atherys.quests.service.DialogAttachmentService;
import com.atherys.quests.service.DialogService;
import com.atherys.quests.service.InventoryService;
import com.atherys.quests.service.ParticleService;
import com.atherys.quests.service.QuestAttachmentService;
import com.atherys.quests.service.QuestLocationService;
import com.atherys.quests.service.QuestMessagingService;
import com.atherys.quests.service.QuestService;
import com.atherys.quests.service.QuesterService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AtherysQuestsModule extends AbstractModule {
    @Override
    protected void configure() {
        // Config
        bind(QuestsConfig.class).in(Scopes.SINGLETON);

        // Registry
        bind(AtherysQuestsRegistry.class).in(Scopes.SINGLETON);

        // Repositories
        bind(QuesterRepository.class).in(Scopes.SINGLETON);
        bind(QuestLocationRepository.class).in(Scopes.SINGLETON);

        // Services
        bind(DialogScriptService.class).to(SimpleDialogScriptService.class).in(Scopes.SINGLETON);
        bind(QuestScriptService.class).to(SimpleQuestScriptService.class).in(Scopes.SINGLETON);
        bind(QuestMessagingService.class).in(Scopes.SINGLETON);
        bind(InventoryService.class).in(Scopes.SINGLETON);
        bind(QuestService.class).in(Scopes.SINGLETON);
        bind(DialogService.class).in(Scopes.SINGLETON);
        bind(QuestLocationService.class).in(Scopes.SINGLETON);
        bind(QuesterService.class).in(Scopes.SINGLETON);
        bind(QuestAttachmentService.class).in(Scopes.SINGLETON);
        bind(DialogAttachmentService.class).in(Scopes.SINGLETON);
        bind(ParticleService.class).in(Scopes.SINGLETON);

        // Facades
        bind(DialogFacade.class).in(Scopes.SINGLETON);
        bind(QuestFacade.class).in(Scopes.SINGLETON);
        bind(QuesterFacade.class).in(Scopes.SINGLETON);

        // Event Listeners
        bind(EntityListener.class).in(Scopes.SINGLETON);
        bind(GsonListener.class).in(Scopes.SINGLETON);
        bind(InventoryListener.class).in(Scopes.SINGLETON);
        bind(MasterEventListener.class).in(Scopes.SINGLETON);
    }
}
