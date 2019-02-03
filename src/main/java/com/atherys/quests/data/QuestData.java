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
public class QuestData extends AbstractData<QuestData, QuestData.Immutable> {

    private String questId;

    QuestData() {
        this("");
        registerGettersAndSetters();
    }

    public QuestData(String questId) {
        this.questId = questId;
        registerGettersAndSetters();
    }

    @Override
    public void registerGettersAndSetters() {
        registerFieldGetter(QuestKeys.QUEST, this::getQuestId);
        registerFieldSetter(QuestKeys.QUEST, this::setQuestId);
        registerKeyValue(QuestKeys.QUEST, this::questId);
    }

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public Value<String> questId() {
        return Sponge.getRegistry().getValueFactory().createValue(QuestKeys.QUEST, questId);
    }

    @Override
    public Optional<QuestData> fill(DataHolder dataHolder) {
        QuestData questData = dataHolder.get(QuestData.class).orElse(null);
        return Optional.ofNullable(questData);
    }

    @Override
    public Optional<QuestData> fill(DataHolder dataHolder, MergeFunction overlap) {
        QuestData questData = overlap.merge(this, dataHolder.get(QuestData.class).orElse(null));
        setQuestId(questData.getQuestId());
        return Optional.of(this);
    }

    @Override
    public Optional<QuestData> from(DataContainer container) {
        if (container.contains(QuestKeys.QUEST.getQuery())) {
            final String questId = container.getString(QuestKeys.QUEST.getQuery()).get();
            setQuestId(questId);

            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Override
    public QuestData copy() {
        return new QuestData(questId);
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(questId);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(QuestKeys.QUEST, this.questId);
    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.096Z")
    public static class Immutable extends AbstractImmutableData<Immutable, QuestData> {

        private String questId;

        public Immutable() {
            this("");
        }

        public Immutable(String questId) {
            this.questId = questId;
            registerGetters();
        }

        @Override
        protected void registerGetters() {
            registerFieldGetter(QuestKeys.QUEST, this::getQuestId);
            registerKeyValue(QuestKeys.QUEST, this::questId);
        }

        private String getQuestId() {
            return questId;
        }

        public ImmutableValue<String> questId() {
            return Sponge.getRegistry().getValueFactory().createValue(QuestKeys.QUEST, questId).asImmutable();
        }


        @Override
        public QuestData asMutable() {
            return new QuestData(this.getQuestId());
        }

        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return super.toContainer()
                    .set(QuestKeys.QUEST, this.getQuestId());
        }

    }

    @Generated(value = "flavor.pie.generator.data.DataManipulatorGenerator", date = "2017-12-26T15:42:38.099Z")
    public static class Builder extends AbstractDataBuilder<QuestData> implements DataManipulatorBuilder<QuestData, Immutable> {

        public Builder() {
            super(QuestData.class, 1);
        }

        @Override
        public QuestData create() {
            return new QuestData();
        }

        @Override
        public Optional<QuestData> createFrom(DataHolder dataHolder) {
            return Optional.of(dataHolder.get(QuestData.class).orElse(new QuestData()));
        }

        @Override
        protected Optional<QuestData> buildContent(DataView container) throws InvalidDataException {
            if (container.contains(QuestKeys.QUEST.getQuery())) {
                final String questId = container.getString(QuestKeys.QUEST.getQuery()).get();
                return Optional.of(new QuestData(questId));
            }
            return Optional.empty();
        }
    }
}
