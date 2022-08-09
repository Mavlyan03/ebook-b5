package kg.eBook.ebookb5.amazons3;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class S3Api {

    private final S3Service s3service;


    public S3Api(S3Service s3service) {
        this.s3service = s3service;
    }

    // upload
    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Map<String, String> upload(@RequestPart(name = "file", required = false)MultipartFile file) throws IOException {
        return s3service.upload(file);
    }

    // delete
    @DeleteMapping("/delete")
    Map<String, String> delete(@RequestParam String fileLink) {
        return s3service.delete(fileLink);
    }
}
