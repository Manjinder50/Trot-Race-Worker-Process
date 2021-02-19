package com.gatewaycorps.assignment.workerProcess.validators;

import com.gatewaycorps.assignment.workerProcess.TrotRaceWorkerProcessApplication;
import com.gatewaycorps.assignment.workerProcess.domain.Horse;
import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import com.gatewaycorps.assignment.workerProcess.executor.TrotRaceTaskExecutor;
import com.gatewaycorps.assignment.workerProcess.repository.RaceDataRepository;
import com.gatewaycorps.assignment.workerProcess.service.RaceDataService;
import com.gatewaycorps.assignment.workerProcess.transformer.RaceDataTransformer;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TrotRaceWorkerProcessApplication.class)
class TrotEventValidatorsTest {

    @Autowired
    Validator validator;

    @MockBean
    TrotRaceTaskExecutor trotRaceTaskExecutor;

    @MockBean
    RaceDataService raceDataService;

    @MockBean
    RaceDataTransformer raceDataTransformer;

    @MockBean
    RaceDataRepository raceDataRepository;

    @Test
    void validateNoErrors() {
        Horse horse = Horse.builder()
                           .id(8L)
                           .name("Dazzle")
                           .build();

        TrotEvent trotEvent = TrotEvent.builder()
                                       .event("finish")
                                       .horse(horse)
                                       .time(1223L)
                                       .build();

        Set<ConstraintViolation<TrotEvent>> validate = validator.validate(trotEvent);
        assertThat(validate).isEmpty();
    }

    @Test
    void validateErrorsInEventTrotDTO() {
        Horse horse = Horse.builder()
                .id(null)
                .name(null)
                .build();

        TrotEvent trotEvent = TrotEvent.builder()
                .event(null)
                .horse(horse)
                .time(null)
                .build();

        Set<ConstraintViolation<TrotEvent>> validate = validator.validate(trotEvent);
        assertThat(validate).hasSize(3);
        assertThat(validate.stream()
                           .map(c->c.getMessage()))
                           .containsExactlyInAnyOrder("TrotEvent and Time cannot be empty or null","Horse Id cannot be empty or null","Horse Name cannot be empty or null");
    }
}