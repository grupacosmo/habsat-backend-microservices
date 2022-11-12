package pl.edu.pk.cosmo.habsatbackend.postsservice.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.PostEntityConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostSlugIsNotUnique;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.MediaRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.PostRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.PostService;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.PostEntityFactory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class PostServiceUnitTest {
    private final PostRepository postRepositoryMock = mock(PostRepository.class);
    private final MediaRepository mediaRepositoryMock = mock(MediaRepository.class);
    private final PostEntityConverter postEntityConverterMock = mock(PostEntityConverter.class);
    private final ModifyPostRequest modifyPostRequestMock = mock(ModifyPostRequest.class);
    private final PostService postService = new PostService(postRepositoryMock, mediaRepositoryMock, postEntityConverterMock);
    private final List<PostEntity> listOfPostEntities = new PostEntityFactory().createMany(2);

    @AfterEach
    public void afterEach() {
        reset(postRepositoryMock, mediaRepositoryMock, postEntityConverterMock, modifyPostRequestMock);
    }


    @Test
    public void shouldFindAllPostsReturnsAllPostEntities() {
        when(postRepositoryMock.findAllByOrderById()).thenReturn(listOfPostEntities);
        assertThat(postService.findAllPosts()).isEqualTo(listOfPostEntities);
    }

    @Test
    public void shouldFindPostByIdReturnsPostEntity() throws PostNotFoundException {
        when(postRepositoryMock.findById(0L)).thenReturn(Optional.of(listOfPostEntities.get(0)));
        assertThat(postService.findPostById(0L)).isEqualTo(listOfPostEntities.get(0));
        verify(postRepositoryMock, times(1)).findById(0L);
    }

    @Test
    public void shouldFindPostByIdThrowsWhenPostNotFound() {
        when(postRepositoryMock.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> postService.findPostById(0L)).isInstanceOf(PostNotFoundException.class);
    }

    @Test
    public void shouldCreatePostCreatesPostEntity() throws MediaNotFoundException, PostSlugIsNotUnique {
        when(postRepositoryMock.existsBySlug(anyString())).thenReturn(false);
        when(postRepositoryMock.save(any(PostEntity.class))).thenReturn(listOfPostEntities.get(0));
        when(modifyPostRequestMock.getThumbnailId()).thenReturn(null);
        when(postEntityConverterMock.of(any(ModifyPostRequest.class), anyString())).thenReturn(listOfPostEntities.get(0));
        assertThat(postService.createPost(modifyPostRequestMock)).isEqualTo(listOfPostEntities.get(0));
    }

    @Test
    public void shouldCreatePostThrowsWhenSlugIsNotUnique() {
        when(modifyPostRequestMock.getSlug()).thenReturn("slug");
        when(postRepositoryMock.existsBySlug(any())).thenReturn(true);
        assertThatThrownBy(() -> postService.createPost(modifyPostRequestMock)).isInstanceOf(PostSlugIsNotUnique.class);
    }

    @Test
    public void shouldCreatePostThrowsWhenThumbnailNotFound() {
        when(postRepositoryMock.existsBySlug(any())).thenReturn(false);
        when(modifyPostRequestMock.getThumbnailId()).thenReturn(0L);
        when(mediaRepositoryMock.existsById(0L)).thenReturn(false);
        assertThatThrownBy(() -> postService.createPost(modifyPostRequestMock)).isInstanceOf(MediaNotFoundException.class);
    }

    @Test
    public void shouldUpdatePostUpdatesPostEntity() throws PostNotFoundException, MediaNotFoundException, PostSlugIsNotUnique {
        when(postRepositoryMock.existsBySlugAndIdNot(anyString(), anyLong())).thenReturn(false);
        when(postRepositoryMock.findById(0L)).thenReturn(Optional.ofNullable(listOfPostEntities.get(0)));
        when(postRepositoryMock.save(any(PostEntity.class))).thenReturn(listOfPostEntities.get(0));
        when(modifyPostRequestMock.getThumbnailId()).thenReturn(null);
        when(postEntityConverterMock.of(any(ModifyPostRequest.class), any(PostEntity.class))).thenReturn(listOfPostEntities.get(0));
        assertThat(postService.updatePost(0L, modifyPostRequestMock)).isEqualTo(listOfPostEntities.get(0));
    }

    @Test
    public void shouldUpdatePostThrowsWhenSlugIsNotUnique() {
        when(modifyPostRequestMock.getSlug()).thenReturn("slug");
        when(postRepositoryMock.existsBySlugAndIdNot("slug", 0L)).thenReturn(true);
        assertThatThrownBy(() -> postService.updatePost(0L, modifyPostRequestMock)).isInstanceOf(PostSlugIsNotUnique.class);
    }

    @Test
    public void shouldUpdatePostThrowsWhenThumbnailNotFound() {
        when(postRepositoryMock.existsBySlugAndIdNot(any(), anyLong())).thenReturn(false);
        when(modifyPostRequestMock.getThumbnailId()).thenReturn(0L);
        when(mediaRepositoryMock.existsById(0L)).thenReturn(false);
        assertThatThrownBy(() -> postService.updatePost(0L, modifyPostRequestMock)).isInstanceOf(MediaNotFoundException.class);
    }

    @Test
    public void shouldDeletePostDeletesPostEntity() {
        postService.deletePost(0L);
        verify(postRepositoryMock, times(1)).deleteById(0L);
    }
}
