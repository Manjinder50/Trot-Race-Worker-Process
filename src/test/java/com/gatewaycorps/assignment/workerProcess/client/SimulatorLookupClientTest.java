package com.gatewaycorps.assignment.workerProcess.client;

import com.gatewaycorps.assignment.workerProcess.domain.TrotEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(SimulatorLookupClient.class)
@ExtendWith(SpringExtension.class)
class SimulatorLookupClientTest {

    @Value("classpath:json/mappings/results1.json")
    private Resource trotRaceResource;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimulatorLookupClient simulatorLookupClient;

    @BeforeEach
    void setUp() {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void whenGetRaceDataFromSimulatorEndPointReturnsCorrectData() throws IOException {

        //given
        String withSuccess = getContentFromResource(trotRaceResource);
        //when
        mockRestServiceServer.expect(requestTo("http://35.207.169.147/results"))
                .andExpect(method(GET))
                .andExpect(header("Accept", containsString("json")))
                .andRespond(withSuccess(withSuccess, MediaType.APPLICATION_JSON));

        TrotEvent trotEventResponse = simulatorLookupClient.getRaceDataFromSimulatorEndPoint();

        assertThat(trotEventResponse.getHorse().getName()).isEqualTo("Dazzle");
        assertThat(trotEventResponse.getHorse().getId()).isEqualTo(8L);
        assertThat(trotEventResponse.getEvent()).isEqualTo("finish");
        assertThat(trotEventResponse.getTime()).isEqualTo(1223L);
    }

    @Test
    void whenGetRaceDataFromSimulatorEndPointReturnsNoContent() {

        //when
        mockRestServiceServer.expect(requestTo("http://35.207.169.147/results"))
                .andExpect(method(GET))
                .andExpect(header("Accept", containsString("json")))
                .andRespond(withNoContent());

        TrotEvent trotEventResponse = simulatorLookupClient.getRaceDataFromSimulatorEndPoint();

        assertThat(trotEventResponse).isNull();
    }

    private static String getContentFromResource(Resource resourceFile) throws IOException {
        return new BufferedReader(new InputStreamReader(resourceFile.getInputStream()))
                .lines().collect(Collectors.joining("\n"));
    }
}