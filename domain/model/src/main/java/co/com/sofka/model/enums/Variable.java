package co.com.sofka.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Variable {
    NAME("name"),
    CODE("code")
    ;
    private final String value;
}
