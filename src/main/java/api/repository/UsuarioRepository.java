package api.repository;

import api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u " +
            "LEFT JOIN FETCH u.usuarioPerfis up " +
            "LEFT JOIN FETCH up.perfil p " +
            "LEFT JOIN FETCH p.permissoes " +
            "WHERE u.userName = :username")
    Optional<Usuario> buscarPorUsuario(@Param("username") String username);


    boolean existsByUserName(String userName);


}
