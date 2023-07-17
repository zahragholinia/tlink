package ir.tinyLink.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ir.tinyLink.model.dto.LinkSrv;
import ir.tinyLink.model.dto.RestResponse;
import ir.tinyLink.service.contract.LinkService;
import ir.tinyLink.validator.ValidUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Validated
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    @PostMapping(value = "/tiny")
    @Operation(summary = "short long link.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.QUERY, name = "longLink", description = "short long link.")
    public ResponseEntity<?> insert(@Valid @ValidUrl String longLink) {

        LinkSrv linkSrv = linkService.insert(longLink);
        return ResponseEntity.ok(
                RestResponse.builder()
                        .result(linkSrv)
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = "")
    @Operation(summary = "get all links.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> list(@RequestParam(required = false) @PositiveOrZero Integer page,
                                  @RequestParam(required = false) @Positive Integer size) {

        Page<LinkSrv> linkVo = linkService.list(page, size);
        return ResponseEntity.ok(
                RestResponse.builder()
                        .count(linkVo.getTotalElements())
                        .result(linkVo.getContent())
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = "/redirect")
    @Operation(summary = "get  tinyLink.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    public RedirectView list(@Valid @ValidUrl String tinyLink) {

        LinkSrv linkVo = linkService.get(tinyLink);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(linkVo.getLongLink());

        return redirectView;
    }

    @GetMapping(value = "/tiny/view")
    @Operation(summary = "get view of link.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> view(@Valid @ValidUrl String tinyLink) {

        Integer linkVo = linkService.view(tinyLink);
        return ResponseEntity.ok(
                RestResponse.builder()
                        .result(linkVo)
                        .status(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(value = "/tiny")
    @Operation(summary = "delete tinyLink.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.PATH, name = "longLink", description = "delete tinyLink")
    public ResponseEntity<?> delete(@Valid @ValidUrl String tinyLink) {

        linkService.delete(tinyLink);
        return ResponseEntity.ok(
                RestResponse.builder()
                        .result(true)
                        .status(HttpStatus.OK.value())
                        .build());
    }
}
