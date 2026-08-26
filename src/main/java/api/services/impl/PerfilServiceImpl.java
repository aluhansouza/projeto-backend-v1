package api.services.impl;

import api.entity.Perfil;
import api.repository.PerfilRepository;
import api.services.interfaces.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilServiceImpl implements PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    public Optional<Perfil> buscarPorNome(String nome) {
        return perfilRepository.findByNome(nome);
    }
}