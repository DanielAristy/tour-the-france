package co.com.sofka.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/country"), handler::listenPOSTCountryUseCase)
                .andRoute(DELETE("/api/country/{name}"), handler::listenDELETECountryUseCase)
                .andRoute(GET("/api/countries"), handler::listenFINDCountryUseCase)
                .andRoute(POST("/api/team"), handler::listenPOSTTeamUseCase)
                ;
    }
}
