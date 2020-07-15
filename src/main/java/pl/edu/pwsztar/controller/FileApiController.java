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

import pl.edu.pwsztar.service.FileService;
import pl.edu.pwsztar.service.MovieService;

import java.io.*;
import java.util.Date;

@Controller
@RequestMapping(value="/api")
public class FileApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieApiController.class);

    private final FileService fileService;

    @Autowired
    public FileApiController(FileService fileService) {
        this.fileService = fileService;
    }

    @CrossOrigin
    @GetMapping(value = "/download-txt")
    public ResponseEntity<Resource> downloadTxt() throws IOException {
        LOGGER.info("--- download txt file ---");

        File file = fileService.createFile();
        InputStreamResource inputStreamResource = fileService.downloadFile(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "test_"+(new Date().getTime())+".txt")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);

    }
}
