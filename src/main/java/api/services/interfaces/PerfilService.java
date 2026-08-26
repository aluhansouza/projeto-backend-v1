
package api.services.interfaces;


import api.entity.Perfil;

import java.util.Optional;

public interface PerfilService {


    Optional<Perfil> buscarPorNome(String nome);


}