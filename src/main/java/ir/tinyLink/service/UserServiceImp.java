package ir.tinyLink.service;


import ir.tinyLink.message.UserMessage;
import ir.tinyLink.model.entity.UserEntity;
import ir.tinyLink.repository.UserRepository;
import ir.tinyLink.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserEntity findLoginUser(){
        Optional<UserEntity> user= userRepository.findByUsername(UserUtil.getCurrentUser().getUsername());

//        if (!user.isPresent())
//            throw new ResourceNotFoundException(UserMessage.errorUserNotFound());

        return user.get();
    }
}
