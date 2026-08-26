package api.services.interfaces;

import api.dto.request.SetorRequestDTO;
import api.dto.response.SetorResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

public interface SetorService {

    PagedModel<EntityModel<SetorResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<SetorResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    SetorResponseDTO buscarPorId(Long id);

    SetorResponseDTO cadastrar(SetorRequestDTO dtoRequest);

    SetorResponseDTO atualizar(Long id, SetorRequestDTO dtoRequest);

    void excluir(Long id);
}
