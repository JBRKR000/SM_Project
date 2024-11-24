package pbs.edu.cooperative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Zastosowanie dla wszystkich ścieżek
                .allowedOrigins("http://localhost:3000")  // Umożliwia dostęp tylko z portu 3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Zezwala na te metody HTTP
                .allowedHeaders("*")  // Zezwala na wszystkie nagłówki
                .exposedHeaders("Authorization")  // Zezwala na wystawianie nagłówka Authorization
                .allowCredentials(true);  // Zezwala na przesyłanie ciasteczek (w przypadku autoryzacji)
    }
}
