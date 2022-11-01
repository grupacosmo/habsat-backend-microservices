package pl.edu.pk.cosmo.habsatbackend.postsservice.resources;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class PostResource {
    private Long id;
    private Long thumbnailId;
    private String title;
    private String slug;
    private String content;
    private String emailOfAuthor;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date publishedAt;
}
