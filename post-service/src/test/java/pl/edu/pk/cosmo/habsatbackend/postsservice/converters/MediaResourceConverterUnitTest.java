package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.junit.jupiter.api.Test;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.MediaResourceConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.resources.MediaResource;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.S3Service;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.MediaEntityFactory;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MediaResourceConverterUnitTest {
    private final S3Service s3ServiceMock = mock(S3Service.class);
    private final MediaResourceConverter mediaResourceConverter = new MediaResourceConverter(s3ServiceMock);

    @Test
    public void shouldConvertMediaEntityIntoMediaResource() throws MalformedURLException {
        when(s3ServiceMock.generateUrl(anyString())).thenReturn(new URL("http://s3.fake/bucket/file.png"));
        MediaEntity entity = new MediaEntityFactory().create();
        MediaResource resource = mediaResourceConverter.of(entity);
        assertThat(resource).isInstanceOf(MediaResource.class);
        assertThat(resource.getId()).isEqualTo(entity.getId());
        assertThat(resource.getNameOfFile()).isEqualTo(entity.getNameOfFile());
        assertThat(resource.getSizeOfFile()).isEqualTo(entity.getSizeOfFile());
        assertThat(resource.getTypeOfFile()).isEqualTo(entity.getTypeOfFile());
        assertThat(resource.getUrl()).isEqualTo("http://s3.fake/bucket/file.png");
        verify(s3ServiceMock, times(1)).generateUrl(entity.getS3Key());
    }
}
