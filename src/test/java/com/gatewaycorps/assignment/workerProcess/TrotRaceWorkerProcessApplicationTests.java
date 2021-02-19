package com.gatewaycorps.assignment.workerProcess;

import com.github.jenspiegsa.wiremockextension.ConfigureWireMock;
import com.github.jenspiegsa.wiremockextension.InjectServer;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.Options;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(classes = TrotRaceWorkerProcessApplication.class,
		properties = {
				"command.line.runner.enabled=false",
				"application.runner.enabled=false" })
@ExtendWith({SpringExtension.class, WireMockExtension.class})
@ActiveProfiles("test")
class TrotRaceWorkerProcessApplicationTests {


	@Value("classpath:json/mappings/results1.json")
	private Resource resultResource1;

	@InjectServer
	WireMockServer wireMockServer;

	@ConfigureWireMock
	Options options = wireMockConfig().port(8088)
			.notifier(new ConsoleNotifier(true));
	String baseUrl;

	@BeforeEach
	void setUp() {
		int port = wireMockServer.port();
		baseUrl = String.format("http://localhost:%s",port);
		System.out.println("baseUrl "+baseUrl);
	}

	/*@Test
	void fetchesDataFromEndPointAndSavesInDB() throws Exception {


		String withSuccess = getContentFromResource(resultResource1);

		stubFor(WireMock.get("/result")
				.willReturn(WireMock.aResponse()
						.withStatus(HttpStatus.OK.value())
						.withBody(withSuccess)));

//		trotRaceTaskExecutor.run("");
	}*/

	private static String getContentFromResource(Resource resourceFile) throws IOException {
		return new BufferedReader(new InputStreamReader(resourceFile.getInputStream()))
				.lines().collect(Collectors.joining("\n"));
	}

}
