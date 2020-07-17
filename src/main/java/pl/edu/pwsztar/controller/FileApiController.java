package pl.edu.pwsztar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.pwsztar.domain.converter.Converter;
import pl.edu.pwsztar.domain.dto.FileDto;
import pl.edu.pwsztar.domain.dto.MovieDto;
import pl.edu.pwsztar.domain.files.FileGenerator;
import pl.edu.pwsztar.service.MovieService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value="/api")
public class FileApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieApiController.class);

    private MovieService movieService;
    private Converter<List<MovieDto>, FileDto> fileMapper;
    private FileGenerator fileGenerator;

    @Autowired
    public FileApiController(MovieService movieService,
                             Converter<List<MovieDto>, FileDto> fileMapper,
                             FileGenerator fileGenerator) {
        this.movieService = movieService;
        this.fileMapper = fileMapper;
        this.fileGenerator = fileGenerator;
    }

    @CrossOrigin
    @GetMapping(value = "/download-txt")
    public ResponseEntity<Resource> downloadTxt() throws IOException {
        LOGGER.info("--- download txt file ---");

        List<MovieDto> movies = movieService.findAllByYearDesc();
        FileDto fileDto = fileMapper.convert(movies);
        InputStreamResource inputStreamResource = fileGenerator.toTxt(fileDto);

        final MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        final String headerAttachmentParam = "attachment;filename=" + fileDto.getFullName();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, headerAttachmentParam)
                .contentType(mediaType)
                .body(inputStreamResource);
    }
}
