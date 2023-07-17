package ir.tinyLink.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.tinyLink.model.vo.ResFact;
import ir.tinyLink.model.vo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/tet")
@Tag(name = "User Management", description = "User Management APIs")
public class UserController {

    @GetMapping(value = "/test")
    @Operation(summary = "Create environment.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.PATH, name = "projectId", description = "id of project.")
    public ResponseEntity<Result<String>> s() {
        return ResponseEntity.ok(ResFact.<String>build()
                .setTotal(1)
                .setResult("ddddddddddddd")
                .get());
    }


}
