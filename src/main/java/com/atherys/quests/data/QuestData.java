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

@Generated( value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.072Z" )
public class QuestData extends AbstractData<QuestData, QuestData.Immutable> {

    private String questId;

    {
        registerGettersAndSetters();
    }

    QuestData() {
        questId = "";
    }

    public QuestData( String questId ) {
        this.questId = questId;
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter( QuestKeys.QUEST, this::getQuestId );
        registerFieldSetter( QuestKeys.QUEST, this::setQuestId );
        registerKeyValue( QuestKeys.QUEST, this::questId );
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId( String questId ) {
        this.questId = questId;
    }

    public Value<String> questId() {
        return Sponge.getRegistry().getValueFactory().createValue( QuestKeys.QUEST, questId );
    }

    @Override
    public Optional<QuestData> fill( DataHolder dataHolder, MergeFunction overlap ) {
        dataHolder.get( QuestData.class ).ifPresent( that -> {
            QuestData data = overlap.merge( this, that );
            this.questId = data.questId;
        } );
        return Optional.of( this );
    }

    @Override
    public Optional<QuestData> from( DataContainer container ) {
        return from( ( DataView ) container );
    }

    public Optional<QuestData> from( DataView container ) {
        container.getObject( QuestKeys.QUEST.getQuery(), String.class ).ifPresent( v -> questId = v );
        return Optional.of( this );
    }

    @Override
    public QuestData copy() {
        return new QuestData( questId );
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable( questId );
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set( QuestKeys.QUEST.getQuery(), questId );
    }

    @Generated( value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.096Z" )
    public static class Immutable extends AbstractImmutableData<Immutable, QuestData> {

        private String questId;

        {
            registerGetters();
        }

        Immutable() {
            questId = "";
        }

        Immutable( String questId ) {
            this.questId = questId;
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter( QuestKeys.QUEST, this::getDialogId );
            registerKeyValue( QuestKeys.QUEST, this::questId );
        }

        public String getDialogId() {
            return questId;
        }

        public ImmutableValue<String> questId() {
            return Sponge.getRegistry().getValueFactory().createValue( QuestKeys.QUEST, questId ).asImmutable();
        }

        @Override
        public QuestData asMutable() {
            return new QuestData( questId );
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set( QuestKeys.QUEST.getQuery(), questId );
        }

    }

    @Generated( value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.099Z" )
    public static class Builder extends AbstractDataBuilder<QuestData> implements DataManipulatorBuilder<QuestData, Immutable> {

        protected Builder() {
            super( QuestData.class, 1 );
        }

        @Override
        public QuestData create() {
            return new QuestData();
        }

        @Override
        public Optional<QuestData> createFrom( DataHolder dataHolder ) {
            return create().fill( dataHolder );
        }

        @Override
        protected Optional<QuestData> buildContent( DataView container ) throws InvalidDataException {
            return create().from( container );
        }

    }
}
