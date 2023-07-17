package ir.tinyLink.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ir.tinyLink.model.srv.ApplicationSrv;
import ir.tinyLink.model.vo.RegisterRequest;
import ir.tinyLink.model.vo.RestResponse;
import ir.tinyLink.service.contract.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidAlgorithmParameterException;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register")
    @Operation(summary = "register user.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.QUERY, name = "user", description = "register user.")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest user) throws InvalidAlgorithmParameterException {

        ApplicationSrv applicationSrv = authenticationService.register(user);
        return ResponseEntity.ok(
                RestResponse.Builder()
                        .result(applicationSrv)
                        .status(HttpStatus.OK)
                        .build());
    }

    @PostMapping(value = "/authenticate")
    @Operation(summary = "Create environment.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.PATH, name = "projectId", description = "id of project.")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody RegisterRequest user) {

        ApplicationSrv applicationSrv = authenticationService.authenticate(user);
        return ResponseEntity.ok(
                RestResponse.Builder()
                        .result(applicationSrv)
                        .status(HttpStatus.OK)
                        .build());
    }

}
