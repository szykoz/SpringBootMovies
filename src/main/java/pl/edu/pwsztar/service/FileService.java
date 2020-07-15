package pl.edu.pwsztar.service;

import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;

public interface FileService {

    File createFile() throws IOException;

    InputStreamResource downloadFile(File tmpFile) throws IOException;
}
