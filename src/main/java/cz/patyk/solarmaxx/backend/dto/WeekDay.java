package cz.patyk.solarmaxx.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeekDay {
    private Byte position;
    private String name;
}
