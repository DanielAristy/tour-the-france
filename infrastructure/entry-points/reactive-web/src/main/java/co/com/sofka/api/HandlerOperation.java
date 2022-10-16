package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.model.country.Country;
import org.reactivecommons.utils.ObjectMapper;

public interface HandlerOperation {

    default Country mapperCountry(CountryDTO countryDTO, ObjectMapper mapper){
        return mapper.map(countryDTO, Country.class);
    }
}
