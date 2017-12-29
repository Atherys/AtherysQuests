package com.atherys.quests.data;

import com.atherys.quests.QuestKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import javax.annotation.Generated;
import java.util.Optional;

@Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.072Z")
public class DialogData extends AbstractData<DialogData, DialogData.Immutable> {

    private String dialogId;

    {
        registerGettersAndSetters();
    }

    DialogData() {
        dialogId = "";
    }

    public DialogData(String dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter( QuestKeys.DIALOG, this::getDialogId );
        registerFieldSetter( QuestKeys.DIALOG, this::setDialogId );
        registerKeyValue( QuestKeys.DIALOG, this::dialogId );
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialog) {
        this.dialogId = dialog;
    }

    public Value<String> dialogId() {
        return Sponge.getRegistry().getValueFactory().createValue( QuestKeys.DIALOG, dialogId );
    }

    @Override
    public Optional<DialogData> fill(DataHolder dataHolder, MergeFunction overlap) {
        dataHolder.get(DialogData.class).ifPresent(that -> {
            DialogData data = overlap.merge(this, that);
            this.dialogId = data.dialogId;
        });
        return Optional.of(this);
    }

    @Override
    public Optional<DialogData> from(DataContainer container) {
        return from((DataView) container);
    }

    public Optional<DialogData> from(DataView container) {
        container.getObject( QuestKeys.DIALOG.getQuery(), String.class).ifPresent(v -> dialogId = v);
        return Optional.of(this);
    }

    @Override
    public DialogData copy() {
        return new DialogData(dialogId);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(dialogId);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set( QuestKeys.DIALOG.getQuery(), dialogId);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.096Z")
    public static class Immutable extends AbstractImmutableData<Immutable, DialogData> {

        private String dialog;
        {
            registerGetters();
        }

        Immutable() {
            dialog = "";
        }

        Immutable(String dialog) {
            this.dialog = dialog;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter( QuestKeys.DIALOG, this::getDialogId);
            registerKeyValue( QuestKeys.DIALOG, this::dialogId);
        }

        public String getDialogId() {
            return dialog;
        }

        public ImmutableValue<String> dialogId() {
            return Sponge.getRegistry().getValueFactory().createValue( QuestKeys.DIALOG, dialog).asImmutable();
        }

        @Override
        public DialogData asMutable() {
            return new DialogData(dialog);
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set( QuestKeys.DIALOG.getQuery(), dialog);
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.099Z")
    public static class Builder extends AbstractDataBuilder<DialogData> implements DataManipulatorBuilder<DialogData, Immutable> {

        protected Builder() {
            super ( DialogData.class, 1 );
        }

        @Override
        public DialogData create() {
            return new DialogData();
        }

        @Override
        public Optional<DialogData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<DialogData> buildContent(DataView container) throws InvalidDataException {
            return create().from(container);
        }

    }
}

