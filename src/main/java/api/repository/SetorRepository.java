package api.repository;

import api.entity.Setor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    @Query("SELECT s FROM Setor s WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Setor> buscarPorNome(@Param("nome") String nome, Pageable pageable);
}
