package com.gatewaycorps.assignment.workerProcess.service;

import com.gatewaycorps.assignment.workerProcess.client.SimulatorLookupClient;
import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import com.gatewaycorps.assignment.workerProcess.entity.TrotEventEntity;
import com.gatewaycorps.assignment.workerProcess.repository.RaceDataRepository;
import com.gatewaycorps.assignment.workerProcess.transformer.RaceDataTransformer;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RaceDataService {

    @Autowired
    private SimulatorLookupClient simulatorLookupClient;

    @Autowired
    private RaceDataRepository repository;

    @Autowired
    private RaceDataTransformer raceDataTransformer;

    private Map<String, TrotEvent> eventMap = new ConcurrentHashMap<>();

    public TrotEvent getTrotRaceData() {
        log.debug("Entering method getTrotRaceData ");
        TrotEvent raceDataFromSimulatorEndPoint = simulatorLookupClient.getRaceDataFromSimulatorEndPoint();
        log.info("RaceDataFromSimulator end point {}",raceDataFromSimulatorEndPoint);
        return raceDataFromSimulatorEndPoint;
    }

    @Transactional
    public void persistDataInDBForFinalEvents(TrotEvent trotEvent) {
        log.info("Entering method persistDataInDBForFinalEvents with trot event {}", trotEvent);
        if ("start".equals(trotEvent.getEvent())) {
            eventMap.put(trotEvent.getHorse().getName(), trotEvent);
        } else {
            if (eventMap.containsKey(trotEvent.getHorse().getName())) {
                TrotEvent eventToBeSaved = eventMap.get(trotEvent.getHorse().getName());
                eventToBeSaved.setTime(trotEvent.getTime());
                TrotEventEntity trotEventEntity = raceDataTransformer.transformDTOToEntity(eventToBeSaved);

                log.info("entity to be saved {}", trotEventEntity);
                Optional.ofNullable(repository.save(trotEventEntity))
                        .orElseThrow(() -> new RuntimeException("Unable to save event with horse id " + trotEventEntity.getHorseId()));
            }
        }
    }
}
