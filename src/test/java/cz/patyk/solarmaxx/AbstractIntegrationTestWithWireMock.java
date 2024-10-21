package cz.patyk.solarmaxx;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import cz.patyk.solarmaxx.backend.utils.FileOperationUtils;
import cz.patyk.solarmaxx.backend.utils.PrefixPath;
import cz.patyk.solarmaxx.dto.wiremock.DataDto;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest
@ActiveProfiles("IT")
public abstract class AbstractIntegrationTestWithWireMock {
    @RegisterExtension
    protected static WireMockExtension wm1 = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();
    protected final WireMockRuntimeInfo wm1RuntimeInfo = wm1.getRuntimeInfo();
    protected final int wiremockPort = wm1RuntimeInfo.getHttpPort();
    protected final FileOperationUtils fileOperationUtils;
    protected final DataDto dataDto = new DataDto(wiremockPort);

    public AbstractIntegrationTestWithWireMock(String filePrefix) {
        fileOperationUtils = new FileOperationUtils(filePrefix);
    }

    protected void setStubGetHttp200UrlEqualTo(String url, String fileName) {
        wm1.stubFor(get(urlEqualTo(url))
                .willReturn(
                        ok(fileOperationUtils.loadFileContextAsString(fileName, PrefixPath.JSON))
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                ));
    }
}
