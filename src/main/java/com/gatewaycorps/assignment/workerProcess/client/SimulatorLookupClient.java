package com.gatewaycorps.assignment.workerProcess.client;

import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class SimulatorLookupClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${simulator.data.url}")
    private String simulatorDataUrl;

    public TrotEvent getRaceDataFromSimulatorEndPoint() {
        log.info("Getting Trot Race Data from Simulator End Point");
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(simulatorDataUrl)
                .build();

        ResponseEntity<TrotEvent> forEntity = restTemplate.getForEntity(uriComponents.toString(), TrotEvent.class);

        return Optional.ofNullable(forEntity)
                .filter(x -> x.getStatusCode() == HttpStatus.OK || x.getStatusCode() == HttpStatus.NO_CONTENT)
                .map(HttpEntity::getBody)
                .orElse(null);
    }

}
