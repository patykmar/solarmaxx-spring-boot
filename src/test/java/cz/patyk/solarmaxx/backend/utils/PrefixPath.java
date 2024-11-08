package cz.patyk.solarmaxx.backend.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrefixPath {
    HTML("html"),
    JSON("json"),
    PEM("pem");

    private final String prefix;
}
