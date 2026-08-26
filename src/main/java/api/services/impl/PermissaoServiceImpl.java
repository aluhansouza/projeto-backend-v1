package api.services.impl;

import api.entity.Permissao;
import api.repository.PermissaoRepository;
import api.services.interfaces.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissaoServiceImpl implements PermissaoService {


    @Autowired
    private PermissaoRepository permissaoRepository;

    public Optional<Permissao> buscarPorNome(String nome) {
        return permissaoRepository.findByNome(nome);
    }



}
