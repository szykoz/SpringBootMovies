package pl.edu.pwsztar.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.domain.dto.MovieDto;
import pl.edu.pwsztar.service.FileService;
import pl.edu.pwsztar.service.MovieService;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    private static final String FILE_NAME = "tmp";
    private static final String EXTENSION = ".txt";
    private final MovieService movieService;

    @Autowired
    public FileServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }


    @Override
    public File createFile() throws IOException {
        return File.createTempFile(FILE_NAME, EXTENSION);
    }

    @Override
    public InputStreamResource downloadFile(File tmpFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
        BufferedWriter bufferedWriter = prepareBufferedWriter(fileOutputStream);
        saveToFile(bufferedWriter);
        closeResources(bufferedWriter, fileOutputStream);

        InputStream stream = new FileInputStream(tmpFile);
        return new InputStreamResource(stream);
    }

    private BufferedWriter prepareBufferedWriter(FileOutputStream fileOutputStream) {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        return new BufferedWriter(outputStreamWriter);
    }

    private void closeResources(BufferedWriter bufferedWriter, FileOutputStream fileOutputStream) throws IOException {
        bufferedWriter.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void saveToFile(BufferedWriter bufferedWriter) throws IOException {
        String allMovies = movieService.findAll()
                .stream()
                .sorted(Comparator.comparingInt(MovieDto::getYear).reversed())
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
        bufferedWriter.write(allMovies);
    }
}
