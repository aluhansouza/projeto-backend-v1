package api.dto.response.auth;

public class UsuarioCadastroResponseDTO {
    private String message;
    public UsuarioCadastroResponseDTO(String message) { this.message = message; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
