package pl.edu.pwsztar.domain.mapper;

import org.springframework.stereotype.Component;
import pl.edu.pwsztar.domain.converter.Converter;
import pl.edu.pwsztar.domain.dto.MovieDto;
import pl.edu.pwsztar.domain.entity.Movie;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieListMapper implements Converter<List<Movie>, List<MovieDto>> {
    @Override
    public List<MovieDto> convert(List<Movie> from) {

        return from.stream()
                .map(movie -> {
                    MovieDto movieDto = new MovieDto();

                    movieDto.setMovieId(movie.getMovieId());
                    movieDto.setTitle(movie.getTitle());
                    movieDto.setImage(movie.getImage());
                    movieDto.setYear(movie.getYear());

                    return movieDto;
                })
                .collect(Collectors.toList());

    }
}
