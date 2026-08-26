package api.controllers;

import api.controllers.docs.AuthControllerDocs;
import api.dto.request.auth.LoginRequestDTO;
import api.dto.request.auth.RefreshTokenRequestDTO;
import api.dto.response.auth.LoginResponseDTO;
import api.dto.response.auth.RefreshTokenResponseDTO;
import api.entity.Usuario;
import api.security.JwtUtil;
import api.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword())
            );

            Usuario usuario = usuarioService.buscarPorUsuario(loginRequest.getUsername());

            // Perfis
            List<String> perfis = usuario.getUsuarioPerfis().stream()
                    .map(usuarioPerfil -> usuarioPerfil.getPerfil().getNome())
                    .distinct()
                    .toList();

            // Permissões
            List<String> permissoes = usuario.getUsuarioPerfis().stream()
                    .flatMap(usuarioPerfil -> usuarioPerfil.getPerfil().getPermissoes().stream())
                    .map(permissao -> permissao.getNome())
                    .distinct()
                    .toList();

            // Gerar JWT com perfis e permissões
            String accessToken = jwtUtil.generateToken(usuario.getUserName(), perfis, permissoes);
            String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername(), perfis, permissoes);

            return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().build();
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);

        Usuario usuario = usuarioService.buscarPorUsuario(username);

        // Perfis
        List<String> perfis = usuario.getUsuarioPerfis().stream()
                .map(usuarioPerfil -> usuarioPerfil.getPerfil().getNome())
                .distinct()
                .toList();

        // Permissões
        List<String> permissoes = usuario.getUsuarioPerfis().stream()
                .flatMap(usuarioPerfil -> usuarioPerfil.getPerfil().getPermissoes().stream())
                .map(permissao -> permissao.getNome())
                .distinct()
                .toList();

        String newAccessToken = jwtUtil.generateToken(username, perfis, permissoes);

        return ResponseEntity.ok(new RefreshTokenResponseDTO(newAccessToken));
    }





}