package com.gatewaycorps.assignment.workerProcess.transformer;

import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import com.gatewaycorps.assignment.workerProcess.entity.TrotEventEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RaceDataTransformer {

    public TrotEventEntity transformDTOToEntity(TrotEvent trotEvent) {
        log.info("Entering method transformDTOToEntity with event {}",trotEvent);
        return TrotEventEntity.builder()
                              .horseId(trotEvent.getHorse().getId())
                              .horseName(trotEvent.getHorse().getName())
                              .time(trotEvent.getTime())
                              .build();
    }
}
