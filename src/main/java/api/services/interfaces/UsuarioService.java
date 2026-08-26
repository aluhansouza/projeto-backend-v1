package api.services.interfaces;

import api.dto.request.auth.UsuarioCadastroRequestDTO;
import api.dto.response.auth.UsuarioCadastroResponseDTO;

public interface UsuarioService {


    UsuarioCadastroResponseDTO cadastro(UsuarioCadastroRequestDTO registroRequest);


}
