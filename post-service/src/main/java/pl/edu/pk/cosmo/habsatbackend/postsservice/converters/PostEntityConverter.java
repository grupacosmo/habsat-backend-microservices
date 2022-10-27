package pl.edu.pk.cosmo.habsatbackend.postsservice.converters;

import org.springframework.stereotype.Component;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;

@Component
public class PostEntityConverter {
    public PostEntity of(ModifyPostRequest modifyPostRequest, String emailOfAuthor) {
        return new PostEntity()
                .setTitle(modifyPostRequest.getTitle())
                .setThumbnailId(modifyPostRequest.getThumbnailId())
                .setSlug(modifyPostRequest.getSlug())
                .setContent(modifyPostRequest.getContent())
                .setPublishedAt(modifyPostRequest.getPublishedAt())
                .setEmailOfAuthor(emailOfAuthor);
    }

    public PostEntity of(ModifyPostRequest modifyPostRequest, PostEntity post) {
        return post
                .setTitle(modifyPostRequest.getTitle())
                .setThumbnailId(modifyPostRequest.getThumbnailId())
                .setSlug(modifyPostRequest.getSlug())
                .setContent(modifyPostRequest.getContent())
                .setPublishedAt(modifyPostRequest.getPublishedAt());
    }
}
