package cz.patyk.solarmaxx.backend.mapper.device.type.output;

import cz.patyk.solarmaxx.backend.dto.device.output.DeviceOutputDto;
import cz.patyk.solarmaxx.backend.entity.Device;

import java.util.List;

public interface OutputIdMapperInterface {
    List<DeviceOutputDto> getDeviceOutputs(Device device, boolean offlineMode);

    List<DeviceOutputDto> getDeviceOutputs(Device device);
}
