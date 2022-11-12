package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.resources.MediaResource;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.S3Service;

@Component
@AllArgsConstructor
public class MediaResourceConverter {
    private final S3Service s3Service;

    public MediaResource of(MediaEntity media) {
        return new MediaResource()
                .setId(media.getId())
                .setUrl(s3Service.generateUrl(media.getS3Key()).toString())
                .setNameOfFile(media.getNameOfFile())
                .setTypeOfFile(media.getTypeOfFile())
                .setSizeOfFile(media.getSizeOfFile());
    }
}
