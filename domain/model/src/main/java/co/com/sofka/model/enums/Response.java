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
    DIGITS("El codigo del ")

    ;

    private final String value;
}
