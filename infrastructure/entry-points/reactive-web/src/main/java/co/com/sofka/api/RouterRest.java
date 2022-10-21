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
        return route(POST("/api/team"), handler::listenPostTeamUseCase)
                .andRoute(DELETE("/api/team/{code}"), handler::listenDeleteTeamUseCase)
                .andRoute(GET("/api/teams"), handler::listenFindTeamUseCase)
                .andRoute(GET("/api/teams/{name}"), handler::listenFindByCountryTeamUseCase)
                .andRoute(POST("/api/team/cyclist"), handler::listenPostCyclistTeamUseCase)
                .andRoute(GET("/api/team/cyclists/{teamCode}"), handler::listenGetCyclistByTeamUseCase)
                .andRoute(POST("/api/country"), handler::listenPostCountryUseCase)
                .andRoute(DELETE("/api/country/{name}"), handler::listenDeleteCountryUseCase)
                .andRoute(GET("/api/countries"), handler::listenFindCountryUseCase)
                .andRoute(GET("/api/cyclists/{nationality}"), handler::listenFindCyclistUseCase)
                ;
    }
}
