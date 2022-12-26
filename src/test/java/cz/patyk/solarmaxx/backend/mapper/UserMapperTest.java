package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.backend.dto.in.UserDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.entity.User;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    public static final String FAKE_EMAIL = "fake@email.com";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";
    public static final String PASSWORD = "som3Str0ngPa$$wor|)";

    public static final User USER_ADMIN = User.builder()
            .id(NumberUtils.LONG_ONE)
            .email(FAKE_EMAIL)
            .roles(ROLE_ADMIN)
            .password(PASSWORD)
            .build();

    public static final UserDtoIn USER_DTO_IN_USER = UserDtoIn.builder()
            .id(NumberUtils.LONG_ONE)
            .email(FAKE_EMAIL)
            .roles(ROLE_USER)
            .password(PASSWORD)
            .build();

    @Test
    void toDtoOut() {
        Assertions.assertThat(USER_MAPPER.toDtoOut(USER_ADMIN))
                .returns(USER_ADMIN.getId(), UserDtoOut::getId)
                .returns(USER_ADMIN.getEmail(), UserDtoOut::getEmail)
                .returns(USER_ADMIN.getRoles(), UserDtoOut::getRoles);
    }

    @Test
    void toEntity() {
        Assertions.assertThat(USER_MAPPER.toEntity(USER_DTO_IN_USER))
                .returns(USER_DTO_IN_USER.getId(), User::getId)
                .returns(USER_DTO_IN_USER.getEmail(), User::getEmail)
                .returns(USER_DTO_IN_USER.getRoles(), User::getRoles)
                .returns(USER_DTO_IN_USER.getPassword(), User::getPassword);
    }
}