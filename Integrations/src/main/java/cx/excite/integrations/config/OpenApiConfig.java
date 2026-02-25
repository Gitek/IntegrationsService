package cx.excite.integrations.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@SecuritySchemes({@SecurityScheme(name = "api-key", scheme = "api-key", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)}
)
public class OpenApiConfig  {
    @Value("${server.url}")
    public String serverUrl;

    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Value("${build.version}")
    private String buildVersion;

    @Bean
    public OpenAPI openApi() {
        Info info = new Info();
        info.setTitle("Integrations API");
        info.version(buildVersion);
        info.setDescription("""
                Welcome to Integrations API.
                                
                This is placeholder text.\040\040
                If this text is here, you don goofed.
                
                """);

        Contact contact = new Contact();
        contact.setName("Excite Technologies");
        contact.setEmail("support@excite.no");
        info.setContact(contact);

        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url(serverUrl + contextPath));

        return new OpenAPI()
                .info(info)
                .servers(servers);
    }
}
