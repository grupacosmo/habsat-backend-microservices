package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import io.sentry.spring.tracing.SentrySpan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.PostEntityConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostSlugIsNotUnique;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.MediaRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.PostRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@SentrySpan
public class PostService {

    private final PostRepository postRepository;
    private final MediaRepository mediaRepository;
    private final PostEntityConverter postEntityConverter;


    public List<PostEntity> findAllPosts() {
        return postRepository.findAllByOrderById();
    }

    public PostEntity findPostById(Long id) throws PostNotFoundException {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public PostEntity createPost(ModifyPostRequest modifyPostRequest) throws PostSlugIsNotUnique, MediaNotFoundException {
        throwIfSlugIsNotUnique(modifyPostRequest.getSlug());
        throwIfThumbnailNotExists(modifyPostRequest.getThumbnailId());

        PostEntity post = postEntityConverter.of(modifyPostRequest, "email_of_author@from_authorization.claims");
        return postRepository.save(post);
    }

    private void throwIfSlugIsNotUnique(String slug) throws PostSlugIsNotUnique {
        if (postRepository.existsBySlug(slug)) throw new PostSlugIsNotUnique();
    }

    @Transactional
    public PostEntity updatePost(Long id, ModifyPostRequest modifyPostRequest) throws PostSlugIsNotUnique, PostNotFoundException, MediaNotFoundException {
        throwIfSlugIsNotUnique(modifyPostRequest.getSlug(), id);
        throwIfThumbnailNotExists(modifyPostRequest.getThumbnailId());

        PostEntity currentPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        PostEntity updatedPost = postEntityConverter.of(modifyPostRequest, currentPost);
        return postRepository.save(updatedPost);
    }

    private void throwIfSlugIsNotUnique(String slug, Long id) throws PostSlugIsNotUnique {
        if (postRepository.existsBySlugAndIdNot(slug, id)) throw new PostSlugIsNotUnique();
    }

    private void throwIfThumbnailNotExists(Long thumbnailId) throws MediaNotFoundException {
        if (thumbnailId != null && !mediaRepository.existsById(thumbnailId)) throw new MediaNotFoundException();
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
