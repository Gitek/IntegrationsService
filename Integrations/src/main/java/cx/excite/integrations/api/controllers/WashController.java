package cx.excite.integrations.api.controllers;

import cx.excite.integrations.auth.Validator;
import cx.excite.integrations.database.entity.PortalProduct;
import cx.excite.integrations.service.DataService;
import cx.excite.integrations.service.LicenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WashController {
    private final DataService dataService;
    private final Validator validator;
    private final LicenseService licenseService;

    public WashController(DataService dataService, Validator validator, LicenseService licenseService) {
        this.dataService = dataService;
        this.validator = validator;
        this.licenseService = licenseService;
    }

    @Operation(summary = "Wash license.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Invalid api key")
    })
    @PostMapping("v1/wash/license")
    @SecurityRequirement(name = "api-key")
    public ResponseEntity<String> washLicense(@RequestHeader HttpHeaders headers) {
        try {
            validator.validateApiKey(headers);

            licenseService.deleteDisabledOrgs();
            licenseService.deleteDuplicates();

            List<PortalProduct> licenses = licenseService.getLicenses();
            LogManager.getLogger().info("Number of inherit licenses: " + licenses.size());
            if (licenses.size() > 0) {
                licenseService.addLicenses(licenses);
            }
            return packResponse("Wash completed", HttpStatus.OK);

        } catch (Exception e) {
            throw handleError(e);
        }
    }


    public ResponseEntity packResponse(Object response, HttpStatus status) {
        var headers = new HttpHeaders();
        var result = new ResponseEntity(response, headers, status);
        return result;
    }

    public ResponseStatusException handleError(Exception ex) {
        ResponseStatusException result;
        if (ex.getClass() == ResponseStatusException.class) {
            result = (ResponseStatusException) ex;
        } else {
            result = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            result.setTitle(ex.getMessage());
            LogManager.getLogger().error(ex.getMessage());
        }
        return result;
    }
}
