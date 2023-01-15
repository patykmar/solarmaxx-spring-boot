package cz.patyk.solarmaxx.backend.dto.relay.output;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasmotaOutputDto {
    @JsonAlias({"POWER", "POWER1", "POWER2", "POWER3", "POWER4",
            "POWER5", "POWER6", "POWER7", "POWER8", "POWER9"})
    private String state;
}
