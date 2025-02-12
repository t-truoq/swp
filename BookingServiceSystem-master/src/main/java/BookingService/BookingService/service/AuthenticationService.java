package BookingService.BookingService.service;

import BookingService.BookingService.dto.request.AuthenticationRequest;
import BookingService.BookingService.dto.request.IntrospectRequest;
import BookingService.BookingService.dto.response.AuthenticationResponse;
import BookingService.BookingService.dto.response.IntrospectResponse;
import BookingService.BookingService.entity.User;
import BookingService.BookingService.exception.AppException;
import BookingService.BookingService.exception.ErrorCode;
import BookingService.BookingService.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        if (!StringUtils.hasText(token)) {
            return IntrospectResponse.builder().valid(false).build();
        }

        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        boolean verified = signedJWT.verify(verifier);

        Date expiry = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean valid = verified && expiry.after(new Date());

        return IntrospectResponse.builder().valid(valid).build();
    }

    private String generateToken(User user) {
        try {
            // Tạo header
            JWSHeader header = new JWSHeader(com.nimbusds.jose.JWSAlgorithm.HS512);

            // Tạo claims
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getEmail())
                    .issuer("BookingService")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                    .claim("role", user.getRole().name())
                    .build();

            // Payload
            Payload payload = new Payload(claimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);

            // Ký
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Error generating token", e);
            throw new RuntimeException(e);
        }
    }
}