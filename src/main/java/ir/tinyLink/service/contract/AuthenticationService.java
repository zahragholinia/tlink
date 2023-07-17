package ir.tinyLink.service.contract;

import ir.tinyLink.model.dto.ApplicationSrv;
import ir.tinyLink.model.dto.RegisterRequest;

import java.security.InvalidAlgorithmParameterException;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
public interface AuthenticationService {

    ApplicationSrv register(RegisterRequest registerRequest) throws InvalidAlgorithmParameterException;

    ApplicationSrv authenticate(RegisterRequest registerRequest);

}
