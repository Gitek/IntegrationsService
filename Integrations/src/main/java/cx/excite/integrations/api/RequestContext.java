package cx.excite.integrations.api;

import cx.excite.integrations.auth.TokenValidator;
import cx.excite.integrations.auth.ValidatedToken;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

public class RequestContext {
    public static final String X_CORRELATION_ID = "X-Correlation-Id";
    private TokenValidator validator;
    public String token; // bearerToken
    public int ekey; // Logged-in user's ekey
    public String sid; // OIDC sid, always equal to the compatibility field session_state (which was used from old in Keycloak)
    public String corrId; // X-Correlation-ID
    public boolean admin;

    public RequestContext(TokenValidator tokenValidator, HttpHeaders headers) {
        this.validator = tokenValidator;
        this.token = "";
        this.ekey = 0;
        this.sid = "";
        this.corrId = "";
        this.admin = false;

        pickupHeaders(headers);
    }
    public void empty() {
        this.token = "";
        this.ekey = 0;
        this.sid = "";
        this.corrId = "";
        this.admin = false;
    }
    public void pickupHeaders(HttpHeaders headers) {
        // token
        token = validator.getAuthorization(headers, "Bearer");
        ValidatedToken validatedToken = validator.validateToken(token);
        ekey = validatedToken.getEkey();
        sid = validatedToken.getPayload().getString("sid");
        boolean isAuthenticated = (ekey != 0) && (sid != null) && (sid.length() > 0);
        if (!isAuthenticated)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authenticated");

        admin = validatedToken.isAdmin();

        corrId = "";
        if (headers.containsKey(X_CORRELATION_ID)) {
            corrId = Objects.requireNonNull(headers.get(X_CORRELATION_ID)).get(0).trim();
        }
        if (corrId == "") {
            corrId = UUID.randomUUID().toString();
        }
    }

    public ResponseEntity packResponse(Object response, HttpStatus status) {
        var headers = new HttpHeaders();
        headers.set(X_CORRELATION_ID, corrId);
        var result = new ResponseEntity(response, headers, status);
        return result;
    }
    public ResponseEntity packResponse(Object response) {
        return packResponse(response, HttpStatus.OK);
    }

    public static ResponseStatusException handleError(Exception ex) {
        ResponseStatusException result;
        if (ex.getClass() == ResponseStatusException.class) {
            result = (ResponseStatusException) ex;
        } else {
            result = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            result.setTitle(ex.getMessage());
            //result.setType(new URI("https://excite.cx/social/internal-server-error"));
            LogManager.getLogger().error(ex.getMessage());
        }
        //result.getHeaders().add(X_CORRELATION_ID, corrId);
        return result;
    }
}
