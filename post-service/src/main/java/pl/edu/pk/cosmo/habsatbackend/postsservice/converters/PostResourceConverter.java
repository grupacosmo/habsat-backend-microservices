package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.resources.PostResource;

@Component
public class PostResourceConverter {
    public PostResource of(PostEntity post) {
        return new PostResource()
                .setId(post.getId())
                .setThumbnailId(post.getThumbnailId())
                .setTitle(post.getTitle())
                .setSlug(post.getSlug())
                .setContent(post.getContent())
                .setEmailOfAuthor(post.getEmailOfAuthor())
                .setPublishedAt(post.getPublishedAt());
    }
}
