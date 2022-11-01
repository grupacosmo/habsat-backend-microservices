package pl.edu.pk.cosmo.habsatbackend.postsservice.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import pl.edu.pk.cosmo.habsatbackend.postsservice.E2ETest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.PostRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.PostEntityFactory;

import java.util.List;

import static org.assertj.db.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerE2ETest extends E2ETest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldFindAllPostsReturnsAllPosts() throws Exception {
        List<PostEntity> listOfPostEntities = new PostEntityFactory().createMany(2);
        postRepository.saveAll(listOfPostEntities);

        mvc.perform(get(api("/posts")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[1]").exists())
                .andExpect(jsonPath("$.[2]").doesNotExist());
    }

    @Test
    public void shouldFindPostByIdReturnPost() throws Exception {
        PostEntity postEntity = postRepository.saveAndFlush(new PostEntityFactory().create());

        mvc.perform(get(api("/posts/" + postEntity.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postEntity.getId()))
                .andExpect(jsonPath("$.thumbnailId").value(postEntity.getThumbnailId()))
                .andExpect(jsonPath("$.title").value(postEntity.getTitle()))
                .andExpect(jsonPath("$.slug").value(postEntity.getSlug()))
                .andExpect(jsonPath("$.content").value(postEntity.getContent()))
                .andExpect(jsonPath("$.emailOfAuthor").value(postEntity.getEmailOfAuthor()));
    }

    @Test
    public void shouldCreatePostCreatesAndReturnsPost() throws Exception {
        JSONObject payload = new JSONObject(getModifyPostRequestPayload());
        mvc.perform(post(api("/posts")).contentType(MediaType.APPLICATION_JSON).content(getModifyPostRequestPayload()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(payload.get("title")))
                .andExpect(jsonPath("$.slug").value(payload.get("slug")))
                .andExpect(jsonPath("$.content").value(payload.get("content")))
                .andExpect(jsonPath("$.publishedAt").value(payload.get("publishedAt")))
                .andExpect(jsonPath("$.emailOfAuthor").value("email_of_author@from_authorization.claims"));


        assertThat(table("posts"))
                .hasNumberOfRows(1)
                .column("id").value().isNumber()
                .column("thumbnail_id").value().isNull()
                .column("title").value().isEqualTo(payload.get("title"))
                .column("slug").value().isEqualTo(payload.get("slug"))
                .column("content").value().isEqualTo(payload.get("content"))
                .column("email_of_author").value().isEqualTo("email_of_author@from_authorization.claims");
    }

    @Test
    public void shouldUpdatePostUpdatesAndReturnsPost() throws Exception {
        PostEntity postEntity = postRepository.saveAndFlush(new PostEntityFactory().create());

        JSONObject payload = new JSONObject(getModifyPostRequestPayload());
        mvc.perform(put(api("/posts/" + postEntity.getId())).contentType(MediaType.APPLICATION_JSON).content(getModifyPostRequestPayload()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postEntity.getId()))
                .andExpect(jsonPath("$.title").value(payload.get("title")))
                .andExpect(jsonPath("$.slug").value(payload.get("slug")))
                .andExpect(jsonPath("$.content").value(payload.get("content")))
                .andExpect(jsonPath("$.publishedAt").value(payload.get("publishedAt")))
                .andExpect(jsonPath("$.emailOfAuthor").value(postEntity.getEmailOfAuthor()));

        assertThat(table("posts"))
                .hasNumberOfRows(1)
                .column("id").value().isEqualTo(postEntity.getId())
                .column("thumbnail_id").value().isNull()
                .column("title").value().isEqualTo(payload.get("title"))
                .column("slug").value().isEqualTo(payload.get("slug"))
                .column("content").value().isEqualTo(payload.get("content"))
                .column("email_of_author").value().isEqualTo(postEntity.getEmailOfAuthor());
    }

    private String getModifyPostRequestPayload() {
        return "{" +
                "\"thumbnailId\":null," +
                "\"title\":\"Why should you test your code?\"," +
                "\"slug\":\"why-should-you-test-your-code\"," +
                "\"content\":\"<h1>Intro</h1><p>Lorem ipsum dolor sit amend...</p1>\"," +
                "\"publishedAt\":\"2022-11-05T19:48:23.000+00:00\"" +
                "}";
    }

    @Test
    public void shouldDeletePostDeletesPost() throws Exception {
        PostEntity postEntity = postRepository.saveAndFlush(new PostEntityFactory().create());
        mvc.perform(delete(api("/posts/" + postEntity.getId())))
                .andExpect(status().isNoContent());

        assertThat(table("posts")).hasNumberOfRows(0);
    }
}
