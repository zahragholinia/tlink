package ir.tinyLink.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ir.tinyLink.model.srv.LinkSrv;
import ir.tinyLink.model.vo.RestResponse;
import ir.tinyLink.service.contract.LinkService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.MalformedURLException;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/link")
public class LinkController {

    private final LinkService linkService;

    @PostMapping(value = "/tiny")
    @Operation(summary = "short long link.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.PATH, name = "longLink", description = "short long link.")
    public ResponseEntity<?> register(
            @Valid  @RequestBody String longLink) throws MalformedURLException {

        LinkSrv linkSrv = linkService.insert(longLink);
        return ResponseEntity.ok(
                RestResponse.Builder()
                        .count(1L)
                        .result(linkSrv)
                        .status(HttpStatus.OK)
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
                 RestResponse.Builder()
                         .count(linkVo.getTotalElements())
                         .result(linkVo.getContent())
                         .status(HttpStatus.OK)
                         .build());
    }

    @GetMapping(value = "/tiny")
    @Operation(summary = "get  longlink.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    public RedirectView list(@Valid @URL String shortLink) throws MalformedURLException {

        LinkSrv linkVo = linkService.get(shortLink);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(linkVo.getLongLink());

        return redirectView;
    }

    @GetMapping(value = "/tiny/view")
    @Operation(summary = "get view of link.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> view(@Valid @URL String shortLink) throws MalformedURLException {

        Integer linkVo = linkService.view(shortLink);
        return ResponseEntity.ok(
                RestResponse.Builder()
                        .count(1L)
                        .result(linkVo)
                        .status(HttpStatus.OK)
                        .build());
    }

    @DeleteMapping(value = "/short/{id}")
    @Operation(summary = "delete tinyLink.")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(mediaType = "application/json"))
    @Parameter(in = ParameterIn.PATH, name = "longLink", description = "delete tinyLink")
    public ResponseEntity<?> delete(
            @PathVariable(value = "id") long id) {

        linkService.delete(id);
        return ResponseEntity.ok(
                RestResponse.Builder()
                        .count(1L)
                        .result(true)
                        .status(HttpStatus.OK)
                        .build());
    }
}
