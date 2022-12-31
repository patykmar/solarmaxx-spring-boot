package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.UserDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<UserDtoIn, UserDtoOut, User> {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final ErrorHandleService errorHandleService;

    @Override
    public List<UserDtoOut> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDtoOut)
                .toList();
    }

    @Override
    public UserDtoOut getOne(Long id) {
        return mapper.toDtoOut(getOneEntity(id));
    }

    @Override
    public User getOneEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> errorHandleService.handleNotFoundError(id, ServiceConstants.USER_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserDtoOut newItem(UserDtoIn dtoIn) {
        User user = mapper.toEntity(dtoIn);
        return mapper.toDtoOut(repository.save(user));
    }

    @Override
    public UserDtoOut editItem(UserDtoIn dtoIn, Long id) {
        checkIfExistEntity(id);
        User user = mapper.toEntity(dtoIn);
        user.setId(id);
        return mapper.toDtoOut(repository.save(user));
    }

    @Override
    public void deleteItem(Long id) {
        checkIfExistEntity(id);
        repository.deleteById(id);
    }

    protected void checkIfExistEntity(Long id) {
        if (!repository.existsById(id)) {
            throw errorHandleService.handleNotFoundError(id, ServiceConstants.USER_NOT_FOUND_MESSAGE);
        }
    }
}
