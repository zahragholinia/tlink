package ir.tinyLink.service;


import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.UserMessage;
import ir.tinyLink.model.entity.UserEntity;
import ir.tinyLink.repository.UserRepository;
import ir.tinyLink.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final UserRepository userRepository;

    public UserEntity findLoginUser() {
        Optional<UserEntity> user = userRepository.findByUsername(UserUtil.getCurrentUser().getUsername());

        if (!user.isPresent())
            throw new TinyLinkGeneralException(HttpStatus.NOT_FOUND,
                    UserMessage.errorUserNotFound(UserUtil.getCurrentUser().getUsername()));

        return user.get();
    }
}
