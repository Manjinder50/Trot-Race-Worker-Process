package com.gatewaycorps.assignment.workerProcess.service;

import com.gatewaycorps.assignment.workerProcess.client.SimulatorLookupClient;
import com.gatewaycorps.assignment.workerProcess.domain.Horse;
import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import com.gatewaycorps.assignment.workerProcess.repository.RaceDataRepository;
import com.gatewaycorps.assignment.workerProcess.transformer.RaceDataTransformer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaceDataServiceTest {

    @InjectMocks
    private RaceDataService raceDataService;

    @Mock
    private RaceDataRepository raceDataRepository;

    @Mock
    private SimulatorLookupClient simulatorLookupClient;

    @Mock
    private RaceDataTransformer raceDataTransformer;

    private Map<String, TrotEvent> eventMap = new ConcurrentHashMap<>();

    @Test
    void getTrotRaceDataFromSimulatorEndPoint() {

        Horse horse = Horse.builder()
                           .name("dazzler")
                           .id(8L)
                           .build();

        TrotEvent trotEvent = TrotEvent.builder()
                                       .event("finish")
                                       .horse(horse)
                                       .time(1223L)
                                       .build();

        when(simulatorLookupClient.getRaceDataFromSimulatorEndPoint()).thenReturn(trotEvent);

        TrotEvent trotRaceDataActual = raceDataService.getTrotRaceData();

        assertThat(trotRaceDataActual).isNotNull();
        assertThat(trotRaceDataActual.getEvent()).isEqualTo("finish");
        assertThat(trotRaceDataActual.getHorse().getId()).isEqualTo(8L);
        assertThat(trotRaceDataActual.getHorse().getName()).isEqualTo("dazzler");
        assertThat(trotRaceDataActual.getTime()).isEqualTo(1223L);

        verify(simulatorLookupClient).getRaceDataFromSimulatorEndPoint();
        verifyNoMoreInteractions(raceDataRepository);
        verifyNoMoreInteractions(raceDataTransformer);
    }
}