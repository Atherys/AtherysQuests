package com.atherys.quests;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;

public class QuestsConfig extends PluginConfig {

    @Setting( "dialog_msg_delay" )
    public long DIALOG_MESSAGE_DELAY = 2;

    protected QuestsConfig( String directory, String filename ) throws IOException {
        super( directory, filename );
    }
}
