package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

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
        if (postRepository.existsBySlug(modifyPostRequest.getSlug())) {
            throw new PostSlugIsNotUnique();
        }

        Long thumbnailId = modifyPostRequest.getThumbnailId();
        if (thumbnailId != null && !mediaRepository.existsById(thumbnailId)) {
            throw new MediaNotFoundException();
        }

        PostEntity post = postEntityConverter.of(modifyPostRequest, "email_of_author@from_authorization.claims");
        return postRepository.save(post);
    }

    @Transactional
    public PostEntity updatePost(Long id, ModifyPostRequest modifyPostRequest) throws PostSlugIsNotUnique, PostNotFoundException, MediaNotFoundException {
        if (postRepository.existsBySlugAndIdNot(modifyPostRequest.getSlug(), id)) {
            throw new PostSlugIsNotUnique();
        }

        Long thumbnailId = modifyPostRequest.getThumbnailId();
        if (thumbnailId != null && !mediaRepository.existsById(thumbnailId)) {
            throw new MediaNotFoundException();
        }

        PostEntity currentPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        PostEntity updatedPost = postEntityConverter.of(modifyPostRequest, currentPost);
        return postRepository.save(updatedPost);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
