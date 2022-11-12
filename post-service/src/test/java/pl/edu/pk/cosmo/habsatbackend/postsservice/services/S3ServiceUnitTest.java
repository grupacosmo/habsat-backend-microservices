package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.S3Service;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class S3ServiceUnitTest {
    private final AmazonS3 amazonS3Mock = mock(AmazonS3.class);
    private final MultipartFile multipartFileMock = mock(MultipartFile.class);
    private final S3Service s3Service = new S3Service(amazonS3Mock);
    private final ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
    private final ArgumentCaptor<DeleteObjectsRequest> deleteObjectsRequestCaptor = ArgumentCaptor.forClass(DeleteObjectsRequest.class);

    @BeforeAll
    public void beforeAll() {
        ReflectionTestUtils.setField(s3Service, "nameOfBucket", "test-bucket");
    }

    @AfterEach
    public void afterEach() {
        reset(amazonS3Mock, multipartFileMock);
    }

    @Test
    public void shouldUploadFileSendPutRequestToS3AndReturnsS3Key() {
        when(multipartFileMock.getOriginalFilename()).thenReturn("file.png");
        when(multipartFileMock.getSize()).thenReturn(1024L);
        when(multipartFileMock.getContentType()).thenReturn("image/png");

        assertThat(s3Service.uploadFile(multipartFileMock)).matches("^\\d{12}_file.png$");
        verify(amazonS3Mock).putObject(putObjectRequestCaptor.capture());

        PutObjectRequest request = putObjectRequestCaptor.getValue();
        assertThat(request.getBucketName()).isEqualTo("test-bucket");
        assertThat(request.getKey()).matches("^\\d{12}_file.png$");

        ObjectMetadata metadata = request.getMetadata();
        assertThat(metadata.getContentLength()).isEqualTo(1024L);
        assertThat(metadata.getContentType()).isEqualTo("image/png");
    }

    @Test
    public void shouldDeleteFileSendDeleteRequestToS3() {
        s3Service.deleteFile("s3_key");
        verify(amazonS3Mock).deleteObjects(deleteObjectsRequestCaptor.capture());
        DeleteObjectsRequest request = deleteObjectsRequestCaptor.getValue();
        assertThat(request.getKeys().size()).isEqualTo(1);
        assertThat(request.getKeys().get(0).getKey()).isEqualTo("s3_key");
    }

    @Test
    public void shouldGenerateUrlReturnsUrlToS3Resource() throws MalformedURLException {
        URL url = new URL("http://fake.url");
        when(amazonS3Mock.getUrl(anyString(), anyString())).thenReturn(url);
        assertThat(s3Service.generateUrl("s3_key")).isEqualTo(url);
        verify(amazonS3Mock, times(1)).getUrl("test-bucket", "s3_key");
    }
}
