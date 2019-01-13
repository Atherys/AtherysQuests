package com.atherys.quests;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;

public class QuestsConfig extends PluginConfig {

    @Setting("dialog_msg_delay")
    public long DIALOG_MESSAGE_DELAY = 2;

    @Setting("dialogs_folder")
    public String DIALOGS_FOLDER = "dialogs";

    @Setting("quests_folder")
    public String QUESTS_FOLDER = "scripts";

    protected QuestsConfig() throws IOException {
        super("config/" + AtherysQuests.ID, "config.conf");
    }
}
