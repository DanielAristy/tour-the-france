package co.com.sofka.mongo.cyclist;

import co.com.sofka.mongo.country.CountryData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cyclist")
public class CyclistData {
    @Id
    private String id;
    private String name;
    private String code;
    private String teamCode;
    private CountryData country;
}
