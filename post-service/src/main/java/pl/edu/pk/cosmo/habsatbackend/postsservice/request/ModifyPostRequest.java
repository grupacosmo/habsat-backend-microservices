package pl.edu.pk.cosmo.habsatbackend.postsservice.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import pl.edu.pk.cosmo.habsatbackend.postsservice.validators.Alphanumeric;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPostRequest {
    @NotBlank
    @Length(max = 256)
    private String title;

    @Nullable
    @Positive
    private Long thumbnailId;

    @NotBlank
    @Length(max = 256)
    @Alphanumeric
    private String slug;

    @NotBlank
    private String content;

    private Date publishedAt;
}
