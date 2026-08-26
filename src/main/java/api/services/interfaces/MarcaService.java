package api.services.interfaces;

import api.dto.request.MarcaRequestDTO;
import api.dto.response.MarcaResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface MarcaService {

    PagedModel<EntityModel<MarcaResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<MarcaResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    MarcaResponseDTO buscarPorId(Long id);

    MarcaResponseDTO cadastrar(MarcaRequestDTO dtoRequest);

    MarcaResponseDTO atualizar(Long id, MarcaRequestDTO dtoRequest);

    void excluir(Long id);
}
