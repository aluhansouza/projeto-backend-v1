package api.services.impl;

import api.controllers.MarcaController;
import api.dto.request.MarcaRequestDTO;
import api.dto.response.MarcaResponseDTO;
import api.entity.Marca;
import api.exceptions.BadRequestException;
import api.exceptions.ResourceNotFoundException;
import api.mapstruct.MarcaMapper;
import api.repository.MarcaRepository;
import api.services.interfaces.MarcaService;
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
public class MarcaServiceImpl implements MarcaService {

    private static final Logger logger = LoggerFactory.getLogger(MarcaServiceImpl.class);

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;
    private final PagedResourcesAssembler<MarcaResponseDTO> assembler;

    @Autowired
    public MarcaServiceImpl(MarcaRepository marcaRepository, MarcaMapper marcaMapper, PagedResourcesAssembler<MarcaResponseDTO> assembler) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
        this.assembler = assembler;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MarcaResponseDTO>> listar(Pageable pageable) {
        Page<Marca> page = marcaRepository.findAll(pageable);

        Page<MarcaResponseDTO> dtoPage = page.map(marca -> {
            MarcaResponseDTO dto = marcaMapper.toResponse(marca);
            addHateoasLinks(dto);
            return dto;
        });

        Link selfLink = linkTo(methodOn(MarcaController.class)
                .listar(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MarcaResponseDTO>> buscarPorNome(String nome, Pageable pageable) {
        Page<Marca> page = marcaRepository.buscarPorNome(nome, pageable);

        Page<MarcaResponseDTO> dtoPage = page.map(marca -> {
            MarcaResponseDTO dto = marcaMapper.toResponse(marca);
            addHateoasLinks(dto);
            return dto;
        });

        Link selfLink = linkTo(methodOn(MarcaController.class)
                .buscarPorNome(nome, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()))
                .withSelfRel();

        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaResponseDTO buscarPorId(Long id) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrado para o ID " + id));

        MarcaResponseDTO dto = marcaMapper.toResponse(marca);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public MarcaResponseDTO cadastrar(MarcaRequestDTO dtoRequest) {
        Marca entity = marcaMapper.toEntity(dtoRequest);
        Marca saved = marcaRepository.save(entity);
        MarcaResponseDTO response = marcaMapper.toResponse(saved);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public MarcaResponseDTO atualizar(Long id, MarcaRequestDTO dtoRequest) {
        Marca entity = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrado com id: " + id));

        marcaMapper.updateFromRequest(dtoRequest, entity);
        Marca updated = marcaRepository.save(entity);

        MarcaResponseDTO response = marcaMapper.toResponse(updated);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (id == null) throw new BadRequestException("ID deve ser informado para excluir um marca");

        Marca entity = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrado para o ID " + id));

        marcaRepository.delete(entity);
    }

    private void addHateoasLinks(MarcaResponseDTO dto) {
        dto.add(linkTo(methodOn(MarcaController.class)
                .listar(0, 0, null))
                .withRel("listar")
                .withType("GET"));

        dto.add(linkTo(methodOn(MarcaController.class)
                .buscarPorNome("", 0, 0, null))
                .withRel("buscarPorNome")
                .withType("GET"));

        dto.add(linkTo(methodOn(MarcaController.class)
                .buscarPorId(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(MarcaController.class)
                .cadastrar(null))
                .withRel("cadastrar")
                .withType("POST"));

        dto.add(linkTo(methodOn(MarcaController.class)
                .atualizar(dto.getId(), null))
                .withRel("atualizar")
                .withType("PUT"));

        dto.add(linkTo(methodOn(MarcaController.class)
                .excluir(dto.getId()))
                .withRel("excluir")
                .withType("DELETE"));
    }
}
