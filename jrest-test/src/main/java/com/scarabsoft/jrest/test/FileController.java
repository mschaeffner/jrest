package com.scarabsoft.jrest.test;

import com.scarabsoft.jrest.test.domain.FileEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/file")
public class FileController {

    @RequestMapping(method = RequestMethod.POST)
    public FileEntity POST(@RequestParam("file") MultipartFile file) {
        return file(file);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public FileEntity PUT(@RequestParam("file") MultipartFile file) {
        return file(file);
    }

    private FileEntity file(MultipartFile file) {
        final FileEntity result = new FileEntity();
        result.setContentType(file.getContentType());
        result.setFilesize(file.getSize());
        result.setFilename(file.getName());
        return result;
    }
}
