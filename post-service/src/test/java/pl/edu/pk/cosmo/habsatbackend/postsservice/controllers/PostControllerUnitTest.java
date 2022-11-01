package pl.edu.pk.cosmo.habsatbackend.postsservice.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import pl.edu.pk.cosmo.habsatbackend.postsservice.controllers.PostController;
import pl.edu.pk.cosmo.habsatbackend.postsservice.converters.PostResourceConverter;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.MediaNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostNotFoundException;
import pl.edu.pk.cosmo.habsatbackend.postsservice.exceptions.PostSlugIsNotUnique;
import pl.edu.pk.cosmo.habsatbackend.postsservice.request.ModifyPostRequest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.services.PostService;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.PostEntityFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerUnitTest {
    private final PostService postServiceMock = mock(PostService.class);
    private final PostResourceConverter postResourceConverterMock = mock(PostResourceConverter.class);
    private final ModifyPostRequest modifyPostRequestMock = mock(ModifyPostRequest.class);
    private PostController postController;
    private List<PostEntity> listOfPostEntities;

    @BeforeAll
    public void beforeAll() {
        postController = new PostController(postServiceMock, postResourceConverterMock);
        listOfPostEntities = new PostEntityFactory().createMany(2);
    }

    @AfterEach
    public void afterEach() {
        reset(postServiceMock, postResourceConverterMock);
    }

    @Test
    public void shouldFindAllPostsAndTransformThemIntoPostResources() {
        when(postServiceMock.findAllPosts()).thenReturn(listOfPostEntities);
        assertThat(postController.findAllPosts().getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postServiceMock, times(1)).findAllPosts();
        verify(postResourceConverterMock, times(1)).of(listOfPostEntities.get(0));
        verify(postResourceConverterMock, times(1)).of(listOfPostEntities.get(1));
    }

    @Test
    public void shouldFindPostByIdAndTransformItIntoPostResource() throws PostNotFoundException {
        when(postServiceMock.findPostById(anyLong())).thenReturn(listOfPostEntities.get(0));
        assertThat(postController.findPostById(0L).getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postServiceMock, times(1)).findPostById(0L);
        verify(postResourceConverterMock, times(1)).of(listOfPostEntities.get(0));
    }

    @Test
    public void shouldCreatePostAndTransformItIntoPostResource() throws MediaNotFoundException, PostSlugIsNotUnique {
        when(postServiceMock.createPost(modifyPostRequestMock)).thenReturn(listOfPostEntities.get(0));
        assertThat(postController.createPost(modifyPostRequestMock).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(postServiceMock, times(1)).createPost(modifyPostRequestMock);
        verify(postResourceConverterMock, times(1)).of(listOfPostEntities.get(0));
    }

    @Test
    public void shouldUpdatePostAndTransformItIntoPostResource() throws PostNotFoundException, MediaNotFoundException, PostSlugIsNotUnique {
        when(postServiceMock.updatePost(0L, modifyPostRequestMock)).thenReturn(listOfPostEntities.get(0));
        assertThat(postController.updatePost(0L, modifyPostRequestMock).getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postServiceMock, times(1)).updatePost(0L, modifyPostRequestMock);
        verify(postResourceConverterMock, times(1)).of(listOfPostEntities.get(0));
    }

    @Test
    public void shouldDeletePostAndReturnEmptyResponse() {
        assertThat(postController.deletePost(0L).getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postServiceMock, times(1)).deletePost(0L);
    }
}
