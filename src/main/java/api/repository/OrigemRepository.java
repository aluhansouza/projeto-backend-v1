package api.repository;

import api.entity.Categoria;
import api.entity.Origem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrigemRepository extends JpaRepository<Origem, Long> {

    @Query("SELECT o FROM Origem o WHERE LOWER(o.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Origem> buscarPorNome(@Param("nome") String nome, Pageable pageable);
}
