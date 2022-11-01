package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.PostEntityFactory;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostEntityConverterUnitTest {
    private final PostEntityConverter postEntityConverter = new PostEntityConverter();
    private ModifyPostRequest modifyPostRequest;

    @BeforeAll
    public void beforeAll() {
        modifyPostRequest = new ModifyPostRequest()
                .setThumbnailId(0L)
                .setTitle("title")
                .setContent("content")
                .setSlug("slug")
                .setPublishedAt(Timestamp.from(Instant.now()));
    }

    @Test
    public void shouldConvertModifyPostRequestAndEmailIntoPostEntity() {
        PostEntity post = postEntityConverter.of(modifyPostRequest, "email");
        assertThat(post).isInstanceOf(PostEntity.class);
        assertThat(post.getId()).isNull();
        assertThat(post.getThumbnailId()).isEqualTo(modifyPostRequest.getThumbnailId());
        assertThat(post.getTitle()).isEqualTo(modifyPostRequest.getTitle());
        assertThat(post.getContent()).isEqualTo(modifyPostRequest.getContent());
        assertThat(post.getSlug()).isEqualTo(modifyPostRequest.getSlug());
        assertThat(post.getPublishedAt()).isEqualTo(modifyPostRequest.getPublishedAt());
        assertThat(post.getEmailOfAuthor()).isEqualTo("email");
    }

    @Test
    public void shouldUpdatePostEntityWithModifyPostRequest() {
        PostEntity originalPost = new PostEntityFactory().create();
        PostEntity currentPost = postEntityConverter.of(modifyPostRequest, originalPost);
        assertThat(currentPost.getId()).isEqualTo(originalPost.getId());
        assertThat(currentPost.getThumbnailId()).isEqualTo(modifyPostRequest.getThumbnailId());
        assertThat(currentPost.getTitle()).isEqualTo(modifyPostRequest.getTitle());
        assertThat(currentPost.getContent()).isEqualTo(modifyPostRequest.getContent());
        assertThat(currentPost.getSlug()).isEqualTo(modifyPostRequest.getSlug());
        assertThat(currentPost.getPublishedAt()).isEqualTo(modifyPostRequest.getPublishedAt());
        assertThat(currentPost.getEmailOfAuthor()).isEqualTo(originalPost.getEmailOfAuthor());
    }
}
