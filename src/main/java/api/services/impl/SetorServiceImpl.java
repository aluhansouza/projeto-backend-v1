package api.services.impl;

import api.controllers.SetorController;
import api.dto.request.SetorRequestDTO;
import api.dto.response.SetorResponseDTO;
import api.entity.Setor;
import api.exceptions.BadRequestException;
import api.exceptions.ResourceNotFoundException;
import api.mapstruct.SetorMapper;
import api.repository.SetorRepository;
import api.services.interfaces.SetorService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class SetorServiceImpl implements SetorService {

    private static final Logger logger = LoggerFactory.getLogger(SetorServiceImpl.class);

    private final SetorRepository setorRepository;
    private final SetorMapper setorMapper;
    private final PagedResourcesAssembler<SetorResponseDTO> assembler;

    @Autowired
    public SetorServiceImpl(SetorRepository setorRepository, SetorMapper setorMapper, PagedResourcesAssembler<SetorResponseDTO> assembler) {
        this.setorRepository = setorRepository;
        this.setorMapper = setorMapper;
        this.assembler = assembler;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<SetorResponseDTO>> listar(Pageable pageable) {
        Page<Setor> page = setorRepository.findAll(pageable);

        Page<SetorResponseDTO> dtoPage = page.map(setor -> {
            SetorResponseDTO dto = setorMapper.toResponse(setor);
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
    public PagedModel<EntityModel<SetorResponseDTO>> buscarPorNome(String nome, Pageable pageable) {
        Page<Setor> page = setorRepository.buscarPorNome(nome, pageable);

        Page<SetorResponseDTO> dtoPage = page.map(setor -> {
            SetorResponseDTO dto = setorMapper.toResponse(setor);
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
    public SetorResponseDTO buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID " + id));

        SetorResponseDTO dto = setorMapper.toResponse(setor);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public SetorResponseDTO cadastrar(SetorRequestDTO dtoRequest) {
        Setor entity = setorMapper.toEntity(dtoRequest);
        Setor saved = setorRepository.save(entity);
        SetorResponseDTO response = setorMapper.toResponse(saved);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public SetorResponseDTO atualizar(Long id, SetorRequestDTO dtoRequest) {
        Setor entity = setorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado com id: " + id));

        setorMapper.updateFromRequest(dtoRequest, entity);
        Setor updated = setorRepository.save(entity);

        SetorResponseDTO response = setorMapper.toResponse(updated);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (id == null) throw new BadRequestException("ID deve ser informado para excluir um setor");

        Setor entity = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID " + id));

        setorRepository.delete(entity);
    }

    private void addHateoasLinks(SetorResponseDTO dto) {
        dto.add(linkTo(methodOn(SetorController.class)
                .listar(0, 0, null))
                .withRel("listar")
                .withType("GET"));

        dto.add(linkTo(methodOn(SetorController.class)
                .buscarPorNome("", 0, 0, null))
                .withRel("buscarPorNome")
                .withType("GET"));

        dto.add(linkTo(methodOn(SetorController.class)
                .buscarPorId(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(SetorController.class)
                .cadastrar(null))
                .withRel("cadastrar")
                .withType("POST"));

        dto.add(linkTo(methodOn(SetorController.class)
                .atualizar(dto.getId(), null))
                .withRel("atualizar")
                .withType("PUT"));

        dto.add(linkTo(methodOn(SetorController.class)
                .excluir(dto.getId()))
                .withRel("excluir")
                .withType("DELETE"));
    }
}
