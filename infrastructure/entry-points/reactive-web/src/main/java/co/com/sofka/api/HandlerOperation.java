package co.com.sofka.api;

import co.com.sofka.api.dto.CountryDTO;
import co.com.sofka.api.dto.TeamDTO;
import co.com.sofka.model.country.Country;
import co.com.sofka.model.team.Team;
import org.reactivecommons.utils.ObjectMapper;

public interface HandlerOperation {

    default Country countryToEntity(CountryDTO countryDTO, ObjectMapper mapper) {
        return mapper.map(countryDTO, Country.class);
    }

    default CountryDTO countryToDTO(Country country, ObjectMapper mapper) {
        return mapper.map(country, CountryDTO.class);
    }

    default Team teamToEntity(TeamDTO teamDTO, ObjectMapper mapper) {
        return mapper.map(teamDTO, Team.class);
    }

    default TeamDTO teamToDTO(Team team, ObjectMapper mapper) {
        return mapper.map(team, TeamDTO.class);
    }
}
