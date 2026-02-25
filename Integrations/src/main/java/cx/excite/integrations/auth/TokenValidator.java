package cx.excite.integrations.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;

@Component
public class TokenValidator {
    @Value("${keycloak.signature.public-key}")
    private String publicKey;

    public ValidatedToken validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            //Decode public key (The public key is fetched from the admin-portal in keycloak)
            byte[] publicBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey);

            JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwt.getIssuer()).build();
            verifier.verify(token);

            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(jwt.getPayload()));
            JSONObject jsonPayload = new JSONObject(payload);
            JSONObject attribute = jsonPayload.getJSONObject("attributes");

            return ValidatedToken.Builder.builder()
                    .ekey(attribute.getInt("ekey"))
                    .admin(attribute.has("admin"))
                    .payload(jsonPayload)
                    .build();
        } catch (JWTDecodeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        } catch (TokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "It's an older code, sir, it does not check out anymore.");
        } catch (JWTVerificationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to verify token with algorithm and public key");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to validate JWT");
        }
    }

    public String getAuthorization(HttpHeaders headers, String type) {
        if (!headers.containsKey("Authorization")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Authorization in header.");
        }
        try {
            String authorizationHeader = Objects.requireNonNull(headers.get("Authorization")).get(0);
            String[] headerArray = authorizationHeader.split(" ");
            if (headerArray.length != 2)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal content of Authorization scheme.");
            if (!headerArray[0].equals(type)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal Authorization scheme.");
            }
            return headerArray[1];
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error reading Authorization scheme.");
        }
    }
}

