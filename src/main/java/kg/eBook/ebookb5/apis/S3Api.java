package kg.eBook.ebookb5.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.eBook.ebookb5.db.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/files")
@CrossOrigin(origins = "*", originPatterns = "*", maxAge = 5000, exposedHeaders = "Access-Control-Allow-Origin", allowedHeaders = "*")
@Tag(name = "File API", description = "S3 file endpoints")
public class S3Api {

    private final S3Service s3service;

    @Operation(summary = "Upload file", description = "Upload any file to S3 bucket")
    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String, String> upload(@RequestPart(name = "file", required = false)MultipartFile file) throws IOException {
        return s3service.upload(file);
    }

    @Operation(summary = "Delete file", description = "Any user can delete own file")
    @DeleteMapping
    Map<String, String> delete(@RequestParam String fileLink) {
        return s3service.delete(fileLink);
    }
}
