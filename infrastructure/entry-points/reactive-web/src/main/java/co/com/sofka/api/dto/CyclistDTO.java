package co.com.sofka.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CyclistDTO {
    private String name;
    private String code;
    private String teamCode;
    private CountryDTO country;
}
