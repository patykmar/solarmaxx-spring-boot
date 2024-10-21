package cz.patyk.solarmaxx.dto.wiremock;

public class AbstractWiremockDto {
    private static final String WIREMOCK_URL_PATTERN = "http://localhost:%d";
    protected final String wiremockUrl;
    protected final int wiremockPort;

    public AbstractWiremockDto(int port) {
        wiremockUrl = String.format(WIREMOCK_URL_PATTERN, port);
        wiremockPort = port;
    }
}
