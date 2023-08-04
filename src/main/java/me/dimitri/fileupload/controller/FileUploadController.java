package me.dimitri.fileupload.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import me.dimitri.fileupload.service.FileUploadService;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

//@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/upload")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(consumes = {MediaType.MULTIPART_FORM_DATA})
    public HttpResponse<?> uploadFile(@NotNull CompletedFileUpload file) throws IOException {
        boolean success = fileUploadService.saveFile(file);
        if (success) {
            return HttpResponse.ok("{\"response\": \"File uploaded successfully\"}");
        }

        return HttpResponse.badRequest("{\"response\": \"File upload failed\"}");
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/test")
    public HttpResponse<?> test() {
        System.out.println("test");
        return HttpResponse.ok("test");
    }
}
