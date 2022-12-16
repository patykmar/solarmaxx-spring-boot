package cz.patyk.solarmaxx.backend.dto.out;

import cz.patyk.solarmaxx.backend.dto.device.output.DeviceOutputDto;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDtoOut {
    private Long id;
    private UserDtoOut user;
    private DeviceTypeDtoOut deviceTypeDtoOut;
    private String name;
    private String ipAddress;
    private Short port;
    private Byte outputCount;
    private List<DeviceOutputDto> deviceOutputDtos;
}
