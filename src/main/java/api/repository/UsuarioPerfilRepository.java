package api.repository;

import api.entity.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Long> {
}