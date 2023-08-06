package me.dimitri.fileupload.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import me.dimitri.fileupload.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.IOException;

//@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/upload")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post(consumes = {MediaType.MULTIPART_FORM_DATA})
    public HttpResponse<?> uploadFile(@NotNull CompletedFileUpload file, HttpRequest request) throws IOException {
        boolean success = fileUploadService.saveFile(file, request);
        if (success) {
            return HttpResponse.ok("{\"response\": \"File uploaded successfully\"}");
        }

        return HttpResponse.badRequest("{\"response\": \"File upload failed\"}");
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/test")
    public HttpResponse<?> test() {
        return HttpResponse.ok("{\"response\": \"Success\"}");
    }
}
