package api.services.impl;

import api.controllers.OrigemController;
import api.controllers.SetorController;
import api.dto.request.OrigemRequestDTO;
import api.dto.response.OrigemResponseDTO;
import api.entity.Origem;
import api.exceptions.BadRequestException;
import api.exceptions.ResourceNotFoundException;
import api.mapstruct.OrigemMapper;
import api.repository.OrigemRepository;
import api.services.interfaces.OrigemService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class OrigemServiceImpl implements OrigemService {

    private static final Logger logger = LoggerFactory.getLogger(OrigemServiceImpl.class);

    private final OrigemRepository origemRepository;
    private final OrigemMapper origemMapper;
    private final PagedResourcesAssembler<OrigemResponseDTO> assembler;

    @Autowired
    public OrigemServiceImpl(OrigemRepository origemRepository, OrigemMapper origemMapper, PagedResourcesAssembler<OrigemResponseDTO> assembler) {
        this.origemRepository = origemRepository;
        this.origemMapper = origemMapper;
        this.assembler = assembler;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<OrigemResponseDTO>> listar(Pageable pageable) {
        Page<Origem> page = origemRepository.findAll(pageable);

        Page<OrigemResponseDTO> dtoPage = page.map(origem -> {
            OrigemResponseDTO dto = origemMapper.toResponse(origem);
            addHateoasLinks(dto);
            return dto;
        });

        Link selfLink = linkTo(methodOn(SetorController.class)
                .listar(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<OrigemResponseDTO>> buscarPorNome(String nome, Pageable pageable) {
        Page<Origem> page = origemRepository.buscarPorNome(nome, pageable);

        Page<OrigemResponseDTO> dtoPage = page.map(origem -> {
            OrigemResponseDTO dto = origemMapper.toResponse(origem);
            addHateoasLinks(dto);
            return dto;
        });

        Link selfLink = linkTo(methodOn(SetorController.class)
                .buscarPorNome(nome, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public OrigemResponseDTO buscarPorId(Long id) {
        Origem origem = origemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Origem não encontrado para o ID " + id));

        OrigemResponseDTO dto = origemMapper.toResponse(origem);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public OrigemResponseDTO cadastrar(OrigemRequestDTO dtoRequest) {
        Origem entity = origemMapper.toEntity(dtoRequest);
        Origem saved = origemRepository.save(entity);
        OrigemResponseDTO response = origemMapper.toResponse(saved);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public OrigemResponseDTO atualizar(Long id, OrigemRequestDTO dtoRequest) {
        Origem entity = origemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrado com id: " + id));

        origemMapper.updateFromRequest(dtoRequest, entity);
        Origem updated = origemRepository.save(entity);

        OrigemResponseDTO response = origemMapper.toResponse(updated);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (id == null) throw new BadRequestException("ID deve ser informado para excluir um setor");

        Origem entity = origemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID " + id));

        origemRepository.delete(entity);
    }

    private void addHateoasLinks(OrigemResponseDTO dto) {
        dto.add(linkTo(methodOn(OrigemController.class)
                .listar(0, 0, null))
                .withRel("listar")
                .withType("GET"));

        dto.add(linkTo(methodOn(OrigemController.class)
                .buscarPorNome("", 0, 0, null))
                .withRel("buscarPorNome")
                .withType("GET"));

        dto.add(linkTo(methodOn(OrigemController.class)
                .buscarPorId(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(OrigemController.class)
                .cadastrar(null))
                .withRel("cadastrar")
                .withType("POST"));

        dto.add(linkTo(methodOn(OrigemController.class)
                .atualizar(dto.getId(), null))
                .withRel("atualizar")
                .withType("PUT"));

        dto.add(linkTo(methodOn(OrigemController.class)
                .excluir(dto.getId()))
                .withRel("excluir")
                .withType("DELETE"));
    }
}
