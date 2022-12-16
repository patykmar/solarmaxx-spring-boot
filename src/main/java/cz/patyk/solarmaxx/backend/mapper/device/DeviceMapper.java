package cz.patyk.solarmaxx.backend.mapper.device;

import cz.patyk.solarmaxx.backend.dto.in.DeviceDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.DeviceDtoOut;
import cz.patyk.solarmaxx.backend.entity.Device;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper {
    public abstract DeviceDtoOut toDtoOut(Device device);

    public abstract Device toEntityDevice(DeviceDtoIn deviceDtoIn);
}
