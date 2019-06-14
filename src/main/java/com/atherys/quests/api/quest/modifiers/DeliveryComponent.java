package com.atherys.quests.api.quest.modifiers;

import com.atherys.quests.api.base.Prototype;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

public class DeliveryComponent implements Prototype<DeliveryComponent> {
    @Expose
    private final Text message;

    public DeliveryComponent(Text message) {
        this.message = message;
    }

    public Text getMessage() {
        return message;
    }

    public DeliveryComponent copy() {
        return this;
    }
}
