package com.atherys.quests.script;

import com.atherys.quests.AtherysQuests;
import com.atherys.quests.api.script.QuestScript;
import com.atherys.quests.api.script.ScriptService;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Script loading pipeline:<br>
 *     Start Plugin -> Start JS Service -> Load Script Files
 */
public class JavaScriptService implements ScriptService {

    private final static JavaScriptService instance = new JavaScriptService();

    private Map<String, JSQuestScript> scripts = new HashMap<>();

    private JavaScriptService() {
        String folder = AtherysQuests.getInstance().getWorkingDirectory() + "/" + AtherysQuests.getConfig().SCRIPTS_FOLDER;

        try {
            loadScripts(new File(folder));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startScripts();
    }

    public static JavaScriptService getInstance() {
        return instance;
    }

    /**
     * Load all script files within the given folder.
     * A script file's name ( ex. "scriptFile.js" ) will reflect which quest will use it.
     * A script file's name should be identical to the quest id in order for it to be used for the relevant quest.
     *
     * @param folder The File representing the directory.
     * @throws FileNotFoundException If the file could not be found
     */
    public void loadScripts(@Nonnull File folder) throws IOException {
        if (!folder.isDirectory()) return;

        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("Could not list files in provided directory.");
        } else {
            for (File file : files) {
                if (!file.getName().endsWith(".js")) continue;

                JSQuestScript script = JSQuestScript.fromFile(file);
                registerScript(script);
            }
        }
    }

    public void registerScript(JSQuestScript script) {
        this.scripts.put(script.getId(), script);
    }

    public void startScripts() {
        scripts.forEach((id, script) -> script.onStart());
    }

    @Override
    public Optional<QuestScript> getQuestScriptById(String scriptId) {
        return Optional.ofNullable(scripts.get(scriptId));
    }
}
