package pl.edu.pwsztar.domain.mapper;

import org.springframework.stereotype.Component;
import pl.edu.pwsztar.domain.converter.Converter;
import pl.edu.pwsztar.domain.dto.FileDto;
import pl.edu.pwsztar.domain.dto.MovieDto;

import java.util.Date;
import java.util.List;

@Component
public class FileMapper implements Converter<List<MovieDto>, FileDto> {

    @Override
    public FileDto convert(List<MovieDto> from) {

        String name = "movies_" + new Date().getTime();
        String extension = "txt";

        String content = from.stream()
                .map(movie -> movie.getYear() + " " + movie.getTitle() + "\n")
                .reduce("", String::concat);

        return new FileDto.Builder()
                .name(name)
                .content(content)
                .extension(extension)
                .build();
    }
}
