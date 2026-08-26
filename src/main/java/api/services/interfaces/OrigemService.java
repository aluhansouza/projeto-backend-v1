package api.services.interfaces;

import api.dto.request.OrigemRequestDTO;
import api.dto.response.OrigemResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface OrigemService {

    PagedModel<EntityModel<OrigemResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<OrigemResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    OrigemResponseDTO buscarPorId(Long id);

    OrigemResponseDTO cadastrar(OrigemRequestDTO dtoRequest);

    OrigemResponseDTO atualizar(Long id, OrigemRequestDTO dtoRequest);

    void excluir(Long id);
}
