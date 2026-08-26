package api.mapstruct;

import org.mapstruct.*;
import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import api.entity.Material;
import api.entity.Categoria;
import api.entity.Setor;
import api.entity.Marca;
import api.entity.Origem;

@Mapper(
        componentModel = "spring",  // Usando Spring para injeção de dependência
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE  // Ignorar propriedades nulas
)
public interface MaterialMapper {

    // Mapeamento do DTO para a Entidade Material
    @Mappings({
            @Mapping(source = "categoriaId", target = "categoria"),
            @Mapping(source = "setorId", target = "setor"),
            @Mapping(source = "marcaId", target = "marca"),
            @Mapping(source = "origemId", target = "origem")
    })
    Material toEntity(MaterialRequestDTO dto);

    // Mapeamento da Entidade Material para o DTO MaterialResponseDTO
    @Mappings({
            @Mapping(source = "categoria.id", target = "categoriaId"), // Mapeia o ID de Categoria para categoriaId
            @Mapping(source = "setor.id", target = "setorId"),         // Mapeia o ID de Setor para setorId
            @Mapping(source = "marca.id", target = "marcaId"),         // Mapeia o ID de Marca para marcaId
            @Mapping(source = "origem.id", target = "origemId")        // Mapeia o ID de Origem para origemId
    })
    MaterialResponseDTO toResponse(Material entity);

    // Método para atualizar a entidade Material com dados do DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Ignorar valores nulos
    @Mappings({
            @Mapping(source = "categoriaId", target = "categoria"),  // Mapeia categoriaId para o objeto Categoria
            @Mapping(source = "setorId", target = "setor"),          // Mapeia setorId para o objeto Setor
            @Mapping(source = "marcaId", target = "marca"),          // Mapeia marcaId para o objeto Marca
            @Mapping(source = "origemId", target = "origem")          // Mapeia origemId para o objeto Origem
    })
    void updateFromRequest(MaterialRequestDTO dto, @MappingTarget Material entity);

    // Helpers para mapear IDs para entidades
    default Categoria mapCategoria(Long id) {
        if (id == null) return null;  // Retorna null caso o ID seja nulo
        Categoria c = new Categoria();  // Cria uma nova instância de Categoria
        c.setId(id);  // Define o ID da Categoria
        return c;
    }

    default Setor mapSetor(Long id) {
        if (id == null) return null;  // Retorna null caso o ID seja nulo
        Setor s = new Setor();  // Cria uma nova instância de Setor
        s.setId(id);  // Define o ID do Setor
        return s;
    }

    default Marca mapMarca(Long id) {
        if (id == null) return null;  // Retorna null caso o ID seja nulo
        Marca s = new Marca();  // Cria uma nova instância da Marca
        s.setId(id);  // Define o ID da Marca
        return s;
    }

    default Origem mapOrigem(Long id) {
        if (id == null) return null;  // Retorna null caso o ID seja nulo
        Origem o = new Origem();  // Cria uma nova instância de Origem
        o.setId(id);  // Define o ID da Origem
        return o;
    }
}
