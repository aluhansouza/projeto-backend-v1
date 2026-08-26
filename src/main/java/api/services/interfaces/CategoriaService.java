package api.services.interfaces;

import api.dto.request.CategoriaRequestDTO;
import api.dto.response.CategoriaResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface CategoriaService {

    PagedModel<EntityModel<CategoriaResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<CategoriaResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    CategoriaResponseDTO buscarPorId(Long id);

    CategoriaResponseDTO cadastrar(CategoriaRequestDTO dtoRequest);

    CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dtoRequest);

    void excluir(Long id);
}
