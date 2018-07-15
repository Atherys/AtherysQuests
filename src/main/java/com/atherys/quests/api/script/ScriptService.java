package com.atherys.quests.api.script;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface ScriptService<T extends Script> {

    /**
     * Load all script files within the given folder.
     * A script file's name ( ex. "scriptFile.js" ) will becomes it's id, without the file extension at the end ( in the example case, ".js" ).
     *
     * @param path The path representing the directory.
     * @throws IOException If the folder could not be found, or a script could not be instantiated from the files within
     */
    void loadAllFromFolder(String path) throws IOException;

    /**
     * Instantiates a {@link T} from the file provided
     *
     * @param file The file to create a {@link T} from.
     * @return The {@link T} instance
     */
    T fromFile(File file) throws IOException;

    void registerScript(T script);

    Optional<T> getScriptById(String id);

    Collection<T> getScripts();

}
