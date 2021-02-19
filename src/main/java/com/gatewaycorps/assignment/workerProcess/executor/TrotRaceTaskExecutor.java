package com.gatewaycorps.assignment.workerProcess.executor;

import com.gatewaycorps.assignment.workerProcess.service.RaceDataService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
@Slf4j
public class TrotRaceTaskExecutor implements CommandLineRunner {

    @Autowired
    RaceDataService raceDataService;

    @Override
    public void run(String... args) {
        log.info("Trot Race Worker process is up and running...");
        log.info("Get env variable hostname {}",System.getenv("hostname"));

        Observable.interval(100, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(raceData -> raceDataService.getTrotRaceData())
                .doOnError(error -> log.info(error.toString()))
                .retry()
                .subscribe(trotEvent-> raceDataService.persistDataInDBForFinalEvents(trotEvent));

        log.info("\n\n\t\t\t ---------  Trot Race Worker process end its work --------- ");
    }
}
