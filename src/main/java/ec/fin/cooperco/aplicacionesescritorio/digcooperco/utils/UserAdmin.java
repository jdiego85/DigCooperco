package ec.fin.cooperco.aplicacionesescritorio.digcooperco.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserAdmin {
    @Value("${users.admin.username}")
    private String username;
    @Value("${users.admin.password}")
    private String password;
}
