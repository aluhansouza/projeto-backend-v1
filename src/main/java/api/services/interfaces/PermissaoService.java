package api.services.interfaces;

import api.entity.Permissao;

import java.util.Optional;

public interface PermissaoService {


    Optional<Permissao> buscarPorNome(String nome);


}
