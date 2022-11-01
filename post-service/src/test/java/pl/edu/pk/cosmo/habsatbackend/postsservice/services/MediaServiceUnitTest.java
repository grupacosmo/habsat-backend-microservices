package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.MediaEntityConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.MediaRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.MediaService;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.S3Service;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.MediaEntityFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

public class MediaServiceUnitTest {
    private final S3Service s3ServiceMock = mock(S3Service.class);
    private final MediaRepository mediaRepositoryMock = mock(MediaRepository.class);
    private final MediaEntityConverter mediaEntityConverterMock = mock(MediaEntityConverter.class);
    private final MultipartFile multipartFileMock = mock(MultipartFile.class);
    private final MediaService mediaService = new MediaService(s3ServiceMock, mediaRepositoryMock, mediaEntityConverterMock);
    private final List<MediaEntity> listOfMediaEntities = new MediaEntityFactory().createMany(2);

    @AfterEach
    public void afterEach() {
        reset(s3ServiceMock, mediaRepositoryMock, mediaEntityConverterMock, multipartFileMock);
    }

    @Test
    public void shouldFindAllMediaReturnsAllMediaEntities() {
        when(mediaRepositoryMock.findAllByOrderById()).thenReturn(listOfMediaEntities);
        assertThat(mediaService.findAllMedia()).isEqualTo(listOfMediaEntities);
    }

    @Test
    public void shouldFindMediaByIdReturnsMediaEntity() throws MediaNotFoundException {
        when(mediaRepositoryMock.findById(0L)).thenReturn(Optional.of(listOfMediaEntities.get(0)));
        assertThat(mediaService.findMediaById(0L)).isEqualTo(listOfMediaEntities.get(0));
        verify(mediaRepositoryMock, times(1)).findById(0L);
    }

    @Test
    public void shouldFindMediaByIdThrowsWhenMediaNotFound() {
        when(mediaRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> mediaService.findMediaById(0L)).isInstanceOf(MediaNotFoundException.class);
    }

    @Test
    public void shouldUploadMediaCreatesMediaEntity() {
        when(s3ServiceMock.uploadFile(multipartFileMock)).thenReturn("s3_key");
        when(mediaEntityConverterMock.of(multipartFileMock, "s3_key")).thenReturn(listOfMediaEntities.get(0));
        when(mediaRepositoryMock.save(any())).thenReturn(listOfMediaEntities.get(0));
        assertThat(mediaService.uploadMedia(multipartFileMock)).isEqualTo(listOfMediaEntities.get(0));
        verify(mediaEntityConverterMock, times(1)).of(multipartFileMock, "s3_key");
        verify(mediaRepositoryMock, times(1)).save(listOfMediaEntities.get(0));
    }

    @Test
    public void shouldUploadMediaUploadsFileToS3() {
        when(s3ServiceMock.uploadFile(multipartFileMock)).thenReturn("s3_key");
        when(mediaEntityConverterMock.of(multipartFileMock, "s3_key")).thenReturn(listOfMediaEntities.get(0));
        when(mediaRepositoryMock.save(any())).thenReturn(listOfMediaEntities.get(0));
        assertThat(mediaService.uploadMedia(multipartFileMock)).isEqualTo(listOfMediaEntities.get(0));
        verify(s3ServiceMock, times(1)).uploadFile(multipartFileMock);
    }

    @Test
    public void shouldDeleteMediaDeletesMediaEntity() throws MediaNotFoundException {
        when(mediaRepositoryMock.findById(0L)).thenReturn(Optional.of(listOfMediaEntities.get(0)));
        mediaService.deleteMedia(0L);
        verify(mediaRepositoryMock, times(1)).delete(listOfMediaEntities.get(0));
    }

    @Test
    public void shouldDeleteMediaRemovesFileFromS3() throws MediaNotFoundException {
        when(mediaRepositoryMock.findById(0L)).thenReturn(Optional.of(listOfMediaEntities.get(0)));
        mediaService.deleteMedia(0L);
        verify(s3ServiceMock, times(1)).deleteFile(listOfMediaEntities.get(0).getS3Key());
    }

    @Test
    public void shouldDeleteMediaThrowsWhenMediaNotFound() {
        when(mediaRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> mediaService.deleteMedia(0L)).isInstanceOf(MediaNotFoundException.class);
    }
}
