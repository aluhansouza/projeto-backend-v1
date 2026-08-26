package api.repository;

import api.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {


    @Query("SELECT m.nome FROM Material m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Material> buscarPorNome(@Param("nome") String nome, Pageable pageable);

    // Usando EntityGraph para buscar as entidades associadas
    @EntityGraph(attributePaths = {"setor", "marca", "categoria", "origem"})
    List<Material> findAll();

    @EntityGraph(attributePaths = {"setor", "marca", "categoria", "origem"})
    Page<Material> findAll(Pageable pageable);



}
