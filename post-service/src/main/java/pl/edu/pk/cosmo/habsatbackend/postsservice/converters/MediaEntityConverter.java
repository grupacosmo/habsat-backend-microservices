package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;

@Component
public class MediaEntityConverter {
    public MediaEntity of(MultipartFile file, String s3Key) {
        return new MediaEntity()
                .setNameOfFile(file.getOriginalFilename())
                .setSizeOfFile(file.getSize())
                .setTypeOfFile(file.getContentType())
                .setS3Key(s3Key);
    }
}
