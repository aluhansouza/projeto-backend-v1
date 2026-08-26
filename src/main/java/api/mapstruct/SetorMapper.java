package api.mapstruct;

import api.dto.request.SetorRequestDTO;
import api.dto.response.SetorResponseDTO;
import api.entity.Setor;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SetorMapper {


    Setor toEntity(SetorRequestDTO dto);


    SetorResponseDTO toResponse(Setor entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(SetorRequestDTO dto, @MappingTarget Setor entity);
}
