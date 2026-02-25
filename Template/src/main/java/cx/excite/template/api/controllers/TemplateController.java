package cx.excite.template.api.controllers;

import cx.excite.template.api.RequestContext;
import cx.excite.template.api.dto.HelloRequest;
import cx.excite.template.api.dto.HelloResponse;
import cx.excite.template.auth.TokenValidator;
import cx.excite.template.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TemplateController {
    private final TemplateService templateService;
    private final TokenValidator tokenValidator;

    public TemplateController(TemplateService templateService, TokenValidator tokenValidator) {
        this.templateService = templateService;
        this.tokenValidator = tokenValidator;
    }

    @Operation(summary = "Hello auth.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Missing token."),
            @ApiResponse(responseCode = "401", description = "Could not verify token.")
    })
    @GetMapping("v1/worlds/auth/hello")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<String> helloAuth(@RequestHeader HttpHeaders headers,
                                            @RequestParam(defaultValue = "excite") String param) {
        var context = new RequestContext(tokenValidator, headers);
        try {

            if (!context.admin)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to perform this operation");

            String response = "This worked with parameter: " + param;

            return context.packResponse(response, HttpStatus.OK);

        } catch (Exception e) {
            throw context.handleError(e);
        }
    }

    @Operation(summary = "Hello.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping("v1/worlds/hello")
    @SecurityRequirement(name = "bearerToken")
    public ResponseEntity<HelloResponse> hello(@RequestHeader HttpHeaders headers,
                                               @RequestBody HelloRequest request) {
        var context = new RequestContext(tokenValidator, headers);
        try {

            HelloResponse response = templateService.hello(request);

            return context.packResponse(response, HttpStatus.OK);

        } catch (Exception e) {
            throw context.handleError(e);
        }
    }
}
