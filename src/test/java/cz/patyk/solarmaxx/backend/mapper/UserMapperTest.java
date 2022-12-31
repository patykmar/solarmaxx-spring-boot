package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
    private static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    @Test
    void toDtoOut() {
        Assertions.assertThat(USER_MAPPER.toDtoOut(EntityConstants.USER_ADMIN))
                .returns(EntityConstants.USER_ADMIN.getId(), UserDtoOut::getId)
                .returns(EntityConstants.USER_ADMIN.getEmail(), UserDtoOut::getEmail)
                .returns(EntityConstants.USER_ADMIN.getRoles(), UserDtoOut::getRoles);
    }

    @Test
    void toEntity() {
        Assertions.assertThat(USER_MAPPER.toEntity(DtoInConstants.USER_DTO_IN_USER))
                .returns(DtoInConstants.USER_DTO_IN_USER.getId(), User::getId)
                .returns(DtoInConstants.USER_DTO_IN_USER.getEmail(), User::getEmail)
                .returns(DtoInConstants.USER_DTO_IN_USER.getRoles(), User::getRoles)
                .returns(DtoInConstants.USER_DTO_IN_USER.getPassword(), User::getPassword);
    }
}