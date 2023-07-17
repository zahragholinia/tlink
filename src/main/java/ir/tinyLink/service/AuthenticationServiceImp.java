package ir.tinyLink.service;


import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.UserMessage;
import ir.tinyLink.model.dto.ApplicationSrv;
import ir.tinyLink.model.dto.RegisterRequest;
import ir.tinyLink.model.entity.UserEntity;
import ir.tinyLink.model.enums.Role;
import ir.tinyLink.repository.UserRepository;
import ir.tinyLink.service.contract.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApplicationSrv register(RegisterRequest registerRequest) {
        var user = saveUser(registerRequest);
        var jwtToken = jwtService.generateToken(user);

        return ApplicationSrv.builder().token(jwtToken).build();
    }

    @Override
    public ApplicationSrv authenticate(RegisterRequest registerRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
        var user = findByUsername(registerRequest);
        var jwtToken = jwtService.generateToken(user);

        return ApplicationSrv.builder().token(jwtToken).build();

    }

    private UserEntity saveUser(RegisterRequest registerRequest) {
        var user = UserEntity.builder().
                username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new TinyLinkGeneralException(HttpStatus.CONFLICT, UserMessage.errorUserDuplicated(registerRequest.getUsername()));
        }

        return user;
    }


    private UserEntity findByUsername(RegisterRequest registerRequest) {
        var user = userRepository.findByUsername(registerRequest.getUsername());
        if (user == null)
            throw new TinyLinkGeneralException(HttpStatus.NOT_FOUND, UserMessage.errorUserNotFound(registerRequest.getUsername()));

        return user.get();
    }
}
