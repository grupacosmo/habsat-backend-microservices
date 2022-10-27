package pl.edu.pk.cosmo.habsatbackend.postsservice.resources;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MediaResource {
    private Long id;
    private String s3Url;
    private String nameOfFile;
    private String typeOfFile;
    private Long sizeOfFile;
}
