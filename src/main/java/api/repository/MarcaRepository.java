package api.repository;

import api.entity.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query("SELECT s FROM Marca s WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Marca> buscarPorNome(@Param("nome") String nome, Pageable pageable);
}
