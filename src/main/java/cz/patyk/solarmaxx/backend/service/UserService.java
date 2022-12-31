package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.UserDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<UserDtoIn, UserDtoOut, User, Long> {

    public UserService(UserRepository repository, UserMapper mapper, ErrorHandleService<Long> errorHandleService) {
        super(repository, mapper, errorHandleService, ServiceConstants.USER_NOT_FOUND_MESSAGE);
    }

}
