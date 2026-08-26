package api.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Aqui você obtém o usuário logado, ex:
        // return Optional.ofNullable(SecurityContextHolder.getContext()
        //          .getAuthentication().getName());
        return Optional.of("system"); // fallback ou stub
    }
}
