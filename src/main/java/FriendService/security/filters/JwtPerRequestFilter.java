package FriendService.security.filters;

import FriendService.security.service.JWTService;
import FriendService.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtPerRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = TokenUtil.parseToken(request);

        if (Optional.ofNullable(token).isPresent()) {
            UsernamePasswordAuthenticationToken details = new UsernamePasswordAuthenticationToken(jwtService.getUserNameFromToken(token),
                    null, jwtService.getRolesFromToken(token).stream().
                    map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(details);
        }

        filterChain.doFilter(request, response);
    }
}
