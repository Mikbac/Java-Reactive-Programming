package pl.mikbac.examples.example100.employeeCRUD.filter;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by MikBac on 11.01.2026
 */

@Service
public class AuthService {

    /**
     * It is just for demo purposes!
     */
    private static final Map<String, UserRole> TOKEN_MAPPING = Map.of(
            "qwerty", UserRole.BASIC,
            "qwerty!", UserRole.ADMIN
    );

    boolean isTokenCorrect(final String token) {
        return TOKEN_MAPPING.containsKey(token);
    }

    UserRole getRole(final String token) {
        return TOKEN_MAPPING.get(token);
    }

}
