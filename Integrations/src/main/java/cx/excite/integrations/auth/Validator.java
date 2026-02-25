package cx.excite.integrations.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class Validator {

    @Value(value = "${api.key}")
    private String apiKey;

    public void validateApiKey(HttpHeaders headers) {
        String key;
        try {
            key = Objects.requireNonNull(headers.get("api-key")).get(0);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid api key");
        }
        if (key == null || !key.equals(apiKey))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid api key");
    }
}
