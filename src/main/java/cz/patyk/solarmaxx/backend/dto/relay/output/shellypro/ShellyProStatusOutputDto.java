package cz.patyk.solarmaxx.backend.dto.relay.output.shellypro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShellyProStatusOutputDto {
    private Integer id;
    private String source;
    @JsonAlias({"output"})
    private Boolean state;
    private Float apower;
    private Float voltage;
    private Integer current;
    private Integer freq;
    private Aenergy aenergy;
    @JsonAlias("ret_aenergy")
    private RetAenergy retAenergy;
    private Temperature temperature;

    @Data
    public static class Aenergy {
        private Float total;
        @JsonAlias("by_minute")
        private List<Float> byMinute;
        @JsonAlias("minute_ts")
        private Long minuteTs;
    }

    @Data
    public static class RetAenergy {
        private float total;
        @JsonAlias("by_minute")
        private List<Float> byMinute;
        @JsonAlias("minute_ts")
        private Long minuteTs;
    }

    @Data
    public static class Temperature {
        private Float tC;
        private Float tF;
    }
}
