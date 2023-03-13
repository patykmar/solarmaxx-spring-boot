package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.RelayOutputScheduleDto;
import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.in.UserDtoIn;
import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Calendar;

public class DtoInConstants {
    private DtoInConstants() {
    }

    public final static RelayTypeDtoIn RELAY_TYPE_DTO_IN_TASMOTA = RelayTypeDtoIn.builder()
            .id(null)
            .name("Relay type TASMOTA DTO IN")
            .urlStatus(TestRelayConstants.URL_STATUS_TASMOTA)
            .urlToggle(TestRelayConstants.URL_TOGGLE_TASMOTA)
            .turnOn(RelayConstants.PORT_STATUS_ON)
            .turnOff(RelayConstants.PORT_STATUS_OFF)
            .deviceTypeString(RelayConstants.DEVICE_TYPE_TASMOTA)
            .build();
    public final static RelayTypeDtoIn RELAY_TYPE_DTO_IN_SHELLY = RelayTypeDtoIn.builder()
            .id(null)
            .name("Relay type SHELLY PRO DTO IN")
            .urlStatus(TestRelayConstants.URL_STATUS_SHELLY_PRO)
            .urlToggle(TestRelayConstants.URL_TOGGLE_SHELLY_PRO)
            .turnOn(Boolean.toString(true))
            .turnOff(Boolean.toString(false))
            .deviceTypeString(RelayConstants.DEVICE_TYPE_SHELLY_PRO)
            .build();
    public static final RelayDtoIn RELAY_DTO_IN = RelayDtoIn.builder()
            .userId(NumberUtils.LONG_ONE)
            .relayTypeDtoId(NumberUtils.LONG_ONE)
            .ipAddress(TestRelayConstants.RELAY_IP)
            .port(TestRelayConstants.RELAY_PORT)
            .outputCount(TestRelayConstants.RELAY_OUTPUT_COUNT)
            .build();

    public static final RelayDto RELAY_DTO = RelayDto.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay dto name")
            .ipAddress(RelayAdapterConstants.FAKE_IP)
            .port((short) 80)
            .userId(NumberUtils.LONG_ONE)
            .relayTypeId(NumberUtils.LONG_ONE)
            .build();
    public static final UserDtoIn USER_DTO_IN_USER = UserDtoIn.builder()
            .id(NumberUtils.LONG_ONE)
            .email(ValueConstants.USER_FAKE_EMAIL)
            .roles(ValueConstants.USER_ROLE_USER)
            .password(ValueConstants.USER_PASSWORD)
            .build();

    public static final RelayScheduleDtoIn RELAY_SCHEDULE_DTO_IN = RelayScheduleDtoIn.builder()
            .relayId(NumberUtils.LONG_ONE)
            .onStart("12:00")
            .onEnd("16:00")
            .dayNumber((byte) 1)
            .build();

    public static final RelayOutputDto RELAY_OUTPUT_DTO_ON = RelayOutputDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description(ValueConstants.RELAY_OUTPUT_DTO_DESCRIPTION)
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(RelayAdapterConstants.TASMOTA_ON)
            .relayId(NumberUtils.LONG_ONE)
            .build();

    public static final RelayOutputDto RELAY_OUTPUT_DTO_OFF = RelayOutputDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description(ValueConstants.RELAY_OUTPUT_DTO_DESCRIPTION)
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(RelayAdapterConstants.TASMOTA_OFF)
            .relayId(NumberUtils.LONG_ONE)
            .build();

    public static final RelayOutputDto RELAY_OUTPUT_DTO_NA = RelayOutputDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description(ValueConstants.RELAY_OUTPUT_DTO_DESCRIPTION)
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(RelayAdapterConstants.TASMOTA_NA)
            .relayId(NumberUtils.LONG_ONE)
            .build();

    public static final RelayOutputScheduleDto RELAY_OUTPUT_SCHEDULE_DTO = RelayOutputScheduleDto.builder()
            .id(NumberUtils.LONG_ONE)
            .relayOutputId(NumberUtils.LONG_ONE)
            .onStart("12:00")
            .onEnd("14:00")
            .dayNumber((byte) Calendar.FRIDAY)
            .build();
}
