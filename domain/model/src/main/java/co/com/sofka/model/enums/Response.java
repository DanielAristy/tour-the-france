package co.com.sofka.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Response {

    RECORD_NOT_FOUND("No existe registro en base de datos"),
    RECORD_DELETED("Se elimino registro correctamente!"),
    PARAMETER_WITH_DIGITS("El parametro debe ser una palabra sin digitos"),
    ALPHANUMERIC("El codigo del equipo debe ser alfanumerico y debe tener minimo 3 caracteres"),
    DIGITS("El codigo del "),
    NOT_FOUND_TEAM("No se ha encontrado equipo en la base de datos!"),
    NOT_FOUND_COUNTRY("No se ha encontrado el pais en base de datos"),
    CYCLIST_NOT_ADDED("No se pudo agregar el ciclista al equipo "),
    UPDATE_TEAM_SUCCESSFULLY("Se ha actualizado el equipo correctamente");

    private final String value;
}
