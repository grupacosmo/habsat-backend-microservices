package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@RequiredArgsConstructor
@SentrySpan
public class S3Service {
    private final AmazonS3 client;

    @Value("${cloud.aws.s3-bucket}")
    private String nameOfBucket;

    @SneakyThrows
    public String uploadFile(MultipartFile file) {
        String s3Key = generateS3Key(file.getOriginalFilename());
        ObjectMetadata metadata = prepareObjectMetadata(file);
        PutObjectRequest request = new PutObjectRequest(nameOfBucket, s3Key, file.getInputStream(), metadata);
        client.putObject(request);
        return s3Key;
    }

    public void deleteFile(String s3Key) {
        DeleteObjectsRequest request = new DeleteObjectsRequest(nameOfBucket);
        request.withKeys(s3Key);
        client.deleteObjects(request);
    }

    public URL generateUrl(String s3Key) {
        return client.getUrl(nameOfBucket, s3Key);
    }

    private ObjectMetadata prepareObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    private String generateS3Key(String fileName) {
        String prefix = RandomStringUtils.randomNumeric(12).toLowerCase();
        return prefix + "_" + fileName;
    }
}
