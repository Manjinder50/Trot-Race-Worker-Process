package com.gatewaycorps.assignment.workerProcess.transformer;

import com.gatewaycorps.assignment.workerProcess.domain.Horse;
import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import com.gatewaycorps.assignment.workerProcess.entity.TrotEventEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RaceDataTransformerTest {

    @InjectMocks
    private RaceDataTransformer raceDataTransformer;

    @Test
    void transformDTOToEntity() {

        Horse horse = Horse.builder()
                .name("dazzler")
                .id(8L)
                .build();

        TrotEvent trotEvent = TrotEvent.builder()
                .event("finish")
                .horse(horse)
                .time(1223L)
                .build();

        TrotEventEntity trotEventEntity = raceDataTransformer.transformDTOToEntity(trotEvent);

        assertThat(trotEventEntity.getHorseId()).isEqualTo(8L);
        assertThat(trotEventEntity.getHorseName()).isEqualTo("dazzler");
        assertThat(trotEventEntity.getTime()).isEqualTo(1223L);
    }
}