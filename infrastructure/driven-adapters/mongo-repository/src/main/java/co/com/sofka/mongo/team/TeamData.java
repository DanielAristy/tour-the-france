package co.com.sofka.mongo.team;
import co.com.sofka.model.country.Country;
import co.com.sofka.model.cyclist.Cyclist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "team")
public class TeamData {

    @Id
    private String id;
    private String name;
    private String code;
    private Country country;
    private List<Cyclist> cyclists;
}
