package cronograma.api.infra;

import jakarta.servlet.http.HttpServletRequest;

public class RecuperarToken {
    public static String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
