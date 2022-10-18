package co.com.sofka.api.dto;

import co.com.sofka.model.cyclist.Cyclist;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private String name;
    private String code;
    private CountryDTO country;
    @JsonIgnore
    private List<Cyclist> cyclists;
}
