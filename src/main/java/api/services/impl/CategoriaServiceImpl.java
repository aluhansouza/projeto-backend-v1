package api.services.impl;

import api.controllers.CategoriaController;
import api.controllers.SetorController;
import api.dto.request.CategoriaRequestDTO;
import api.dto.response.CategoriaResponseDTO;
import api.entity.Categoria;
import api.exceptions.BadRequestException;
import api.exceptions.ResourceNotFoundException;
import api.mapstruct.CategoriaMapper;
import api.repository.CategoriaRepository;
import api.services.interfaces.CategoriaService;
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
public class CategoriaServiceImpl implements CategoriaService {

    private static final Logger logger = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    private final PagedResourcesAssembler<CategoriaResponseDTO> assembler;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, PagedResourcesAssembler<CategoriaResponseDTO> assembler) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.assembler = assembler;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<CategoriaResponseDTO>> listar(Pageable pageable) {
        Page<Categoria> page = categoriaRepository.findAll(pageable);

        Page<CategoriaResponseDTO> dtoPage = page.map(categoria -> {
            CategoriaResponseDTO dto = categoriaMapper.toResponse(categoria);
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
    public PagedModel<EntityModel<CategoriaResponseDTO>> buscarPorNome(String nome, Pageable pageable) {
        Page<Categoria> page = categoriaRepository.buscarPorNome(nome, pageable);

        Page<CategoriaResponseDTO> dtoPage = page.map(categoria -> {
            CategoriaResponseDTO dto = categoriaMapper.toResponse(categoria);
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
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado para o ID " + id));

        CategoriaResponseDTO dto = categoriaMapper.toResponse(categoria);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public CategoriaResponseDTO cadastrar(CategoriaRequestDTO dtoRequest) {
        Categoria entity = categoriaMapper.toEntity(dtoRequest);
        Categoria saved = categoriaRepository.save(entity);
        CategoriaResponseDTO response = categoriaMapper.toResponse(saved);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dtoRequest) {
        Categoria entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado com id: " + id));

        categoriaMapper.updateFromRequest(dtoRequest, entity);
        Categoria updated = categoriaRepository.save(entity);

        CategoriaResponseDTO response = categoriaMapper.toResponse(updated);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if (id == null) throw new BadRequestException("ID deve ser informado para excluir um setor");

        Categoria entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado para o ID " + id));

        categoriaRepository.delete(entity);
    }

    private void addHateoasLinks(CategoriaResponseDTO dto) {
        dto.add(linkTo(methodOn(CategoriaController.class)
                .listar(0, 0, null))
                .withRel("listar")
                .withType("GET"));

        dto.add(linkTo(methodOn(CategoriaController.class)
                .buscarPorNome("", 0, 0, null))
                .withRel("buscarPorNome")
                .withType("GET"));

        dto.add(linkTo(methodOn(CategoriaController.class)
                .buscarPorId(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        dto.add(linkTo(methodOn(CategoriaController.class)
                .cadastrar(null))
                .withRel("cadastrar")
                .withType("POST"));

        dto.add(linkTo(methodOn(CategoriaController.class)
                .atualizar(dto.getId(), null))
                .withRel("atualizar")
                .withType("PUT"));

        dto.add(linkTo(methodOn(CategoriaController.class)
                .excluir(dto.getId()))
                .withRel("excluir")
                .withType("DELETE"));
    }
}
