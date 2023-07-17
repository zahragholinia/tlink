package ir.tinyLink.service;


import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.UserMessage;
import ir.tinyLink.model.entity.UserEntity;
import ir.tinyLink.model.enums.Role;
import ir.tinyLink.model.srv.ApplicationSrv;
import ir.tinyLink.model.vo.RegisterRequest;
import ir.tinyLink.repository.UserRepository;
import ir.tinyLink.service.contract.AuthenticationService;
import ir.tinyLink.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.security.InvalidAlgorithmParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final static String PASSWORD_PATTERN="(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)";

    @Override
    public ApplicationSrv register(RegisterRequest registerRequest)  {
        checkValidPassword(registerRequest.getPassword());
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
        var jwtToken = jwtService.generateToken(user);

        return ApplicationSrv.builder().token(jwtToken).build();
    }

    @Override
    public ApplicationSrv authenticate(RegisterRequest registerRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
        var user = userRepository.findByUsername(registerRequest.getUsername());
        if (user == null)
            throw new UsernameNotFoundException("user not found");

        var jwtToken = jwtService.generateToken(user.get());

        return ApplicationSrv.builder().token(jwtToken).build();

    }

    private void checkValidPassword(String password)  {
       if(!StringUtil.checkPattern(password,PASSWORD_PATTERN))
           throw new TinyLinkGeneralException(HttpStatus.BAD_REQUEST,UserMessage.errorInvalidPassword());

}
}
