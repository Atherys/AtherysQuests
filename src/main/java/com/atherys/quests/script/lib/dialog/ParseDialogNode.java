package com.atherys.quests.script.lib.dialog;

import com.atherys.quests.dialog.tree.DialogNode;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.function.Function;

/*
parseDialogNode(
    {
        id: 0,
        requirements: [
          questRequirement("some-quest-id"),
          currencyRequirement(50.0d, "atherys:argent")
        ]
        player: null,
        npc: [
            textOf(["Hello, friend!"]),
        ]
        responses: [
            {
                id: 1,
                player: textOf(["Greetings, Merchant! Have you any work for me today?"]),
                npc: [
                    textOf(["Work? Oh, you bet."])
                    textOf(["This morning, as I was setting up my stall, a bunch of children went by and stole an entire stack of cocoa beans!"]),
                    textOf(["I reported this to the guards, of course, but they're either incapable or apathetic to the trouble that has befallen me."]),
                    textOf(["Do me a favor, and find those kids. I want my cocoa beans back!"])
                ]
                responses: [
                    {
                        id: 3,
                        player: textOf(["Oh, now that you mention it, I am a bit busy today, so I don't know if I'll find the time."]),
                        npc: [
                            textOf(["No worries, I'm sure the guards will get to it.. eventually..."])
                        ]
                    },
                    {
                        id: 4,
                        player: textOf(["Kids, huh? Well, how hard could it be. I'll certainly take a look around for you."]),
                        npc: [
                            textOf(["Oh, splendid! Thank you very much, I eagerly away to see what you find out."])
                        ],
                        startsQuest: "test-quest"
                    }
                ]
            },
            {
                id: 2,
                player: textOf(["Not now, Merchant."]),
                npc: [
                    textOf(["Another time, perhaps then."])
                ]
            }
        ]
    }
)
*/

public class ParseDialogNode implements Function<ScriptObjectMirror, DialogNode> {
    @Override
    public DialogNode apply(ScriptObjectMirror json) {
        if (json.isEmpty()) return null;

        // TODO

        return null;
    }
}
