package pl.edu.pwsztar.domain.mapper;

import org.springframework.stereotype.Component;
import pl.edu.pwsztar.domain.converter.Converter;
import pl.edu.pwsztar.domain.dto.MovieCounterDto;

@Component
public class MovieCounterMapper implements Converter<Long, MovieCounterDto> {

    @Override
    public MovieCounterDto convert(Long counter) {
        return new MovieCounterDto.Builder().counter(counter).build();
    }
}
