package co.com.sofka.model.cyclist;

import co.com.sofka.model.country.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Cyclist {
    private String name;
    private String code;
    private String teamCode;
    private Country country;
}
