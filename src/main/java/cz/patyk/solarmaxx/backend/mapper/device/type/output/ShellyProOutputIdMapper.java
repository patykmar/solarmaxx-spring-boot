package cz.patyk.solarmaxx.backend.mapper.device.type.output;

import cz.patyk.solarmaxx.backend.dto.device.output.DeviceOutputDto;
import cz.patyk.solarmaxx.backend.entity.Device;

import java.util.List;

public class ShellyProOutputIdMapper implements OutputIdMapperInterface {
    @Override
    public List<DeviceOutputDto> getDeviceOutputs(Device device, boolean offlineMode) {
        return null;
    }

    @Override
    public List<DeviceOutputDto> getDeviceOutputs(Device device) {
        return getDeviceOutputs(device, false);
    }
}
