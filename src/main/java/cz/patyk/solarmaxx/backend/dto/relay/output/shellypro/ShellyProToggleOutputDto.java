package cz.patyk.solarmaxx.backend.dto.relay.output.shellypro;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShellyProToggleOutputDto {
    @JsonAlias({"was_on"})
    private Boolean state;
}
