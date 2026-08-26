package api.services.impl;

import api.controllers.MaterialController;
import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import api.entity.Material;
import api.exceptions.*;
import api.file.exporter.contract.MaterialExporter;
import api.file.exporter.factory.FileExporterFactory;
import api.file.importer.contract.MaterialImporter;
import api.file.importer.factory.FileImporterFactory;
import api.mapstruct.MaterialMapper;
import api.repository.MaterialRepository;
import api.services.interfaces.MaterialService;
import api.services.utils.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MaterialServiceImpl implements MaterialService {


    private final Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);
    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;
    private final FileImporterFactory importer;
    private final FileExporterFactory exporter;
    private final PagedResourcesAssembler<MaterialResponseDTO> assembler;

    @Autowired
    public MaterialServiceImpl(MaterialRepository materialRepository,
                               MaterialMapper materialMapper,
                               FileImporterFactory importer,
                               FileExporterFactory exporter,
                               PagedResourcesAssembler<MaterialResponseDTO> assembler) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
        this.importer = importer;
        this.exporter = exporter;
        this.assembler = assembler;
    }

    @Autowired
    private FileStorageService storageService;

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MaterialResponseDTO>> listar(Pageable pageable) {
        logger.info("Finding all Materiais!");

        // 1. Busca paginada
        Page<Material> page = materialRepository.findAll(pageable);

        // 2. Converte diretamente cada entidade em DTO, já numa Page
        Page<MaterialResponseDTO> dtoPage = page.map(material -> {
            MaterialResponseDTO dto = materialMapper.toResponse(material);
            addHateoasLinks(dto);
            return dto;
        });

        // 3. Monta o link “self” para essa mesma página
        Link selfLink = linkTo(methodOn(MaterialController.class)
                .listar(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()
                ))
                .withSelfRel();

        // 4. Único return: delega ao assembler para criar o PagedModel
        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MaterialResponseDTO>> buscarPorNome(String nome, Pageable pageable) {
        logger.info("Finding Material by name!");

        // 1. Busca paginada filtrando por nome
        Page<Material> page = materialRepository.buscarPorNome(nome, pageable);

        // 2. Converte cada entidade em DTO com MapStruct e adiciona HATEOAS
        Page<MaterialResponseDTO> dtoPage = page.map(material -> {
            MaterialResponseDTO dto = materialMapper.toResponse(material);
            addHateoasLinks(dto);
            return dto;
        });

        // 3. Cria link self para esta consulta
        Link selfLink = linkTo(methodOn(MaterialController.class)
                .buscarPorNome(nome, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString()))
                .withSelfRel();

        // 4. Retorna o modelo de página com metadados de paginação e links
        return assembler.toModel(dtoPage, selfLink);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDTO buscarPorId(Long id) {
        logger.info("Finding one Material!");

        // 1. Busca a entidade ou lança exceção se não existir
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        // 2. Converte para DTO e adiciona links HATEOAS
        MaterialResponseDTO dto = materialMapper.toResponse(material);
        addHateoasLinks(dto);

        // 3. Retorna o DTO completamente populado
        return dto;
    }

    @Override
    @Transactional
    public MaterialResponseDTO cadastrar(MaterialRequestDTO dtoRequest, MultipartFile imagem) {
        if (dtoRequest == null) throw new RequiredObjectIsNullException();
        logger.info("Criando novo Material: nome='{}'", dtoRequest.getNome());

        // 1) Validação da imagem (se enviada)
        if (imagem != null) {
            String contentType = imagem.getContentType();
            if (!contentType.startsWith("image/")) {
                throw new InvalidImageTypeException("O arquivo enviado não é uma imagem.");
            }
        }

        // 2) Persiste sem imagem para gerar o ID
        Material entity = materialMapper.toEntity(dtoRequest);
        Material saved = materialRepository.save(entity);

        // 3) Armazenamento e link da imagem (se houver)
        /*if (imagem != null) {
            // Usando o método storeFile para a subpasta "materiais"
            String nomeArquivo = storageService.storeFile(imagem, "materiais");  // Passando o nome da subpasta
            String urlImagem = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/projeto/uploads/materiais/")  // Caminho correto com a subpasta
                    .path(nomeArquivo)
                    .toUriString();*/

        if (imagem != null) {
            // Usando o método storeFile para a subpasta "materiais"
            String nomeArquivo = storageService.storeFile(imagem, "materiais");  // Passando o nome da subpasta
            String urlImagem = ServletUriComponentsBuilder.fromHttpUrl("http://10.10.0.154:8080")
            //String urlImagem = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/v1/materiais/imagem/")
        .path(nomeArquivo)
        .toUriString();




            entity.setImagemUrl(urlImagem);
            logger.info("Imagem armazenada com URL: {}", urlImagem);

            // Re-salva a entidade com a URL da imagem
            saved = materialRepository.save(entity);
        }

        // 4) QR code (mantém a lógica atual)
       /* String qrUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/materiais/{id}")
                .buildAndExpand(saved.getId())
                .toUriString();*/

        String qrUrl = ServletUriComponentsBuilder.fromHttpUrl("http://10.10.0.55:4200")
                        .path("/recursos/materiais/?editar={id}")

                                .buildAndExpand(saved.getId())
                                        .toUriString();


        saved.setQrValor(qrUrl);

        // Re-salva a entidade com o QR code
        saved = materialRepository.save(saved);

        // 5) Retorno
        MaterialResponseDTO response = materialMapper.toResponse(saved);
        addHateoasLinks(response);
        return response;
    }

    @Override
    @Transactional
    public MaterialResponseDTO atualizar(Long id, MaterialRequestDTO dtoRequest, MultipartFile imagem) {
        if (dtoRequest == null) throw new RequiredObjectIsNullException();
        logger.info("Atualizando Material: id='{}', nome='{}'", id, dtoRequest.getNome());

        // 1) Buscar a entidade Material no banco de dados
        Material entity = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado com id: " + id));

        // 2) Atualiza os dados da entidade com as informações do DTO
        // Aqui você chama o método correto de atualização, que é `updateFromRequest`
        materialMapper.updateFromRequest(dtoRequest, entity); // Atualiza os dados da entidade com o DTO

        // 3) Processar a imagem (se fornecida)
        if (imagem != null && !imagem.isEmpty()) {
            // Remover o arquivo antigo se houver
            if (entity.getImagemUrl() != null) {
                String oldFileName = entity.getImagemUrl().substring(entity.getImagemUrl().lastIndexOf("/") + 1);
                storageService.deleteFile(oldFileName, "materiais");  // Remover o arquivo anterior
            }

            // Armazenar o novo arquivo
            String nomeArquivo = storageService.storeFile(imagem, "materiais");
            String arquivoUrl = ServletUriComponentsBuilder.fromHttpUrl("http://10.10.0.154:8080")
            //String arquivoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/v1/materiais/imagem/")
        .path(nomeArquivo)
        .toUriString();


            // Atualizar a URL da imagem
            entity.setImagemUrl(arquivoUrl);
        }

        // 4) Gerar o QR Code
        String qrUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/materiais/{id}")
                .buildAndExpand(entity.getId())
                .toUriString();
        entity.setQrValor(qrUrl);

        // 5) Salvar a entidade atualizada no banco de dados
        Material updated = materialRepository.save(entity);

        // 6) Retornar a resposta com os dados atualizados
        MaterialResponseDTO response = materialMapper.toResponse(updated);
        addHateoasLinks(response);  // Adiciona os links HATEOAS ao DTO
        return response;
    }


    @Override
    @Transactional
    public void excluir(Long id) {
        // 1. Validação de ID
        if (id == null) {
            logger.error("ID não informado ao tentar excluir material");
            throw new BadRequestException("ID deve ser informado para excluir um material");
        }

        // 2. Log de entrada
        logger.info("Excluindo Material: id={}", id);

        // 3. Busca a entidade ou lança exceção se não existir
        Material entity = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Nenhum material encontrado para o ID %d", id)
                ));

        // 4. Remove a entidade
        materialRepository.delete(entity);
        logger.debug("Material excluído com sucesso: id={}", id);
    }

    @Override
    public Resource exportarPagina(Pageable pageable, String acceptHeader) {
        // 1. Validação de parâmetros
        if (pageable == null) {
            logger.error("Parâmetros de paginação não informados ao exportar página de materiais");
            throw new BadRequestException("Parâmetros de paginação são obrigatórios");
        }
        if (acceptHeader == null || acceptHeader.isBlank()) {
            logger.error("Accept header não informado ao exportar página de materiais");
            throw new BadRequestException("Tipo de mídia (Accept header) é obrigatório para exportação");
        }

        // 2. Log de entrada
        logger.info("Exportando página de Materiais: page={}, size={}, sort={}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        // 3. Busca e mapeia todos os materiais da página para DTOs
        List<MaterialResponseDTO> dtos = materialRepository.findAll(pageable)
                .map(materialMapper::toResponse)
                .stream()
                .peek(this::addHateoasLinks)
                .toList();

        // 4. Seleciona o exporter apropriado e gera o arquivo com tratamento de exceções
        Resource file;
        try {
            MaterialExporter materialExporter = exporter.getExporter(acceptHeader);
            file = materialExporter.exportMateriais(dtos);
        } catch (Exception e) {
            logger.error("Erro ao exportar página de materiais", e);
            throw new FileStorageException("Erro ao exportar página de materiais", e);
        }

        logger.debug("Exportação concluída: {} registros, tipo={}", dtos.size(), acceptHeader);
        return file;
    }

    @Override
    public Resource exportMaterial(Long id, String acceptHeader) {
        // 1. Validações iniciais
        if (id == null) {
            logger.error("ID não informado ao tentar exportar material");
            throw new BadRequestException("ID é obrigatório para exportação");
        }
        if (acceptHeader == null || acceptHeader.isBlank()) {
            logger.error("Accept header não informado ao tentar exportar material id={}", id);
            throw new BadRequestException("Tipo de mídia (Accept header) é obrigatório para exportação");
        }

        // 2. Log de entrada
        logger.info("Exportando dados de Material: id={}, tipo={}", id, acceptHeader);

        // 3. Busca a entidade ou lança exceção
        Material entity = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Nenhum material encontrado para o ID %d", id)
                ));

        // 4. Converte para DTO e adiciona links HATEOAS
        MaterialResponseDTO dto = materialMapper.toResponse(entity);
        addHateoasLinks(dto);

        // 5. Obtém o exporter certo e gera o Resource com tratamento de exceções
        Resource file;
        try {
            MaterialExporter materialExporter = exporter.getExporter(acceptHeader);
            file = materialExporter.exportMaterial(dto);
        } catch (Exception e) {
            logger.error("Erro ao exportar material id={}", id, e);
            throw new FileStorageException("Erro ao exportar material id=" + id, e);
        }

        logger.debug("Exportação concluída para material id={}", id);
        return file;
    }

    @Override
    @Transactional
    public List<MaterialResponseDTO> massCreation(MultipartFile file) {
        // 1. Validação do arquivo
        if (file == null || file.isEmpty()) {
            logger.error("Arquivo de importação vazio ou nulo");
            throw new BadRequestException("Arquivo de importação é obrigatório e não pode estar vazio");
        }

        // 2. Log de entrada
        logger.info("Importando materiais de arquivo: {}", file.getOriginalFilename());

        try (InputStream is = file.getInputStream()) {
            // 3. Determina nome do arquivo
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("Nome do arquivo não pode ser nulo"));
            MaterialImporter importer = this.importer.getImporter(filename);

            // 4. Importa e persiste cada material
            List<Material> entities = importer.importFile(is).stream()
                    .map(materialMapper::toEntity)
                    .map(materialRepository::save)
                    .toList();

            // 5. Converte para DTO, adiciona HATEOAS e coleta resultados
            List<MaterialResponseDTO> dtos = entities.stream()
                    .map(materialMapper::toResponse)
                    .peek(this::addHateoasLinks)
                    .toList();

            logger.debug("Importação concluída: {} materiais importados", dtos.size());
            return dtos;

        } catch (IOException e) {
            logger.error("Erro ao ler o arquivo de importação", e);
            throw new FileStorageException("Erro ao processar o arquivo de importação", e);
        } catch (Exception e) {
            logger.error("Erro inesperado durante importação", e);
            throw new FileStorageException("Erro ao processar o arquivo de importação", e);
        }
    }

    private void addHateoasLinks(MaterialResponseDTO dto) {
        // 1. Listar (coleção) – template para page, size e direction
        dto.add(Link.of(
                linkTo(methodOn(MaterialController.class).listar(0, 0, null)).toUri().toString()
                        .replace("?page=0&size=0&direction=null", "{?page,size,direction}"),
                "listar"
        ).withType("GET"));

        // 2. Buscar por nome (coleção filtrada) – template para nome, page, size e direction
        dto.add(Link.of(
                linkTo(methodOn(MaterialController.class).buscarPorNome("", 0, 0, null)).toUri().toString()
                        .replace("/buscarPorNome/&page=0&size=0&direction=null", "/buscarPorNome/{nome}{?page,size,direction}"),
                "buscarPorNome"
        ).withType("GET"));

        // 3. Self (recurso único)
        dto.add(linkTo(methodOn(MaterialController.class)
                .buscarPorId(dto.getId()))
                .withSelfRel()
                .withType("GET"));

        // 4. Create
        dto.add(linkTo(methodOn(MaterialController.class)
                .cadastrar(null, null))
                .withRel("cadastrar")
                .withType("POST"));

        // 5. Update
        dto.add(linkTo(methodOn(MaterialController.class)
                .atualizar(null,null, null))
                .withRel("atualizar")
                .withType("PUT"));

        // 6. Delete
        dto.add(linkTo(methodOn(MaterialController.class)
                .excluir(dto.getId()))
                .withRel("excluir")
                .withType("DELETE"));
    }
}
