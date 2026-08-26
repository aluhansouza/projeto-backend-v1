package api.mapstruct;

import api.dto.request.MarcaRequestDTO;
import api.dto.response.MarcaResponseDTO;
import api.entity.Marca;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MarcaMapper {


    Marca toEntity(MarcaRequestDTO dto);


    MarcaResponseDTO toResponse(Marca entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(MarcaRequestDTO dto, @MappingTarget Marca entity);
}
