package com.atherys.quests;

import com.atherys.core.utils.PluginConfig;
import com.atherys.script.ScriptConfig;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestsConfig extends PluginConfig {

    @Setting("dialog_msg_delay")
    public long DIALOG_MESSAGE_DELAY = 2;

    @Setting("dialogs_folder")
    public String DIALOGS_FOLDER = "dialogs";

    @Setting("quests_folder")
    public String QUESTS_FOLDER = "scripts";

    @Setting("npcs")
    public Map<String, UUID> NPCS = new HashMap<>();

    @Setting("script_lang")
    public String SCRIPT_TYPE = ScriptConfig.GROOVY_TYPE;

    protected QuestsConfig() throws IOException {
        super("config/" + AtherysQuests.ID, "config.conf");
    }
}
