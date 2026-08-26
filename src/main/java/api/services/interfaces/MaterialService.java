package api.services.interfaces;

import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface MaterialService {


    PagedModel<EntityModel<MaterialResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<MaterialResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    MaterialResponseDTO buscarPorId(Long id);

    MaterialResponseDTO cadastrar(MaterialRequestDTO dtoRequest, MultipartFile imagem);

    MaterialResponseDTO atualizar(Long id,MaterialRequestDTO materialRequestDTO, MultipartFile imagem);

    void excluir(Long id);

    // ---------------------------------------------------------------------------------

    Resource exportarPagina(Pageable pageable, String acceptHeader);

    Resource exportMaterial(Long id, String acceptHeader);

    List<MaterialResponseDTO> massCreation(MultipartFile file);


}