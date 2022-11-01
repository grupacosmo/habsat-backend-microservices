package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.junit.jupiter.api.Test;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.PostResourceConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.resources.PostResource;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.PostEntityFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostResourceConverterUnitTest {
    private final PostResourceConverter postResourceConverter = new PostResourceConverter();

    @Test
    public void shouldConvertPostEntityIntoPostResource() {
        PostEntity entity = new PostEntityFactory().create();
        PostResource resource = postResourceConverter.of(entity);
        assertThat(resource).isInstanceOf(PostResource.class);
        assertThat(resource.getId()).isEqualTo(entity.getId());
        assertThat(resource.getThumbnailId()).isEqualTo(entity.getThumbnailId());
        assertThat(resource.getTitle()).isEqualTo(entity.getTitle());
        assertThat(resource.getSlug()).isEqualTo(entity.getSlug());
        assertThat(resource.getContent()).isEqualTo(entity.getContent());
        assertThat(resource.getEmailOfAuthor()).isEqualTo(entity.getEmailOfAuthor());
        assertThat(resource.getPublishedAt()).isEqualTo(entity.getPublishedAt());
    }
}
