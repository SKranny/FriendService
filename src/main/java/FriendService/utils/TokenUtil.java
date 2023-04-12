package FriendService.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String parseToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
    }
}
