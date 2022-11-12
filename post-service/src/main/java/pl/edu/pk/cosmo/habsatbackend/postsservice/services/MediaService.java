package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.MediaEntityConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.MediaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@SentrySpan
public class MediaService {
    private final S3Service s3Service;
    private final MediaRepository mediaRepository;
    private final MediaEntityConverter mediaEntityConverter;

    public List<MediaEntity> findAllMedia() {
        return mediaRepository.findAllByOrderById();
    }

    public MediaEntity findMediaById(Long id) throws MediaNotFoundException {
        return mediaRepository.findById(id).orElseThrow(MediaNotFoundException::new);
    }

    @Transactional
    public MediaEntity uploadMedia(MultipartFile file) {
        String s3Key = s3Service.uploadFile(file);
        MediaEntity media = mediaEntityConverter.of(file, s3Key);
        return mediaRepository.save(media);
    }

    @Transactional
    public void deleteMedia(Long id) throws MediaNotFoundException {
        MediaEntity media = mediaRepository.findById(id).orElseThrow(MediaNotFoundException::new);
        s3Service.deleteFile(media.getS3Key());
        mediaRepository.delete(media);
    }

}
