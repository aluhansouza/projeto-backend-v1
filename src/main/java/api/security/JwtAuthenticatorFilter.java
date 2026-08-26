package api.security;

import api.services.impl.UsuarioServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticatorFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final UsuarioServiceImpl usuarioService;

    public JwtAuthenticatorFilter(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                if (jwtUtil.validateToken(token)) {
                    username = jwtUtil.getUsernameFromToken(token);
                }
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            List<String> roles = jwtUtil.getRolesFromToken(token);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());


            List<String> permissoes = jwtUtil.getPermissoesFromToken(token);
            if (permissoes != null) {
                authorities.addAll(
                        permissoes.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                );
            }

            UserDetails userDetails = usuarioService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}