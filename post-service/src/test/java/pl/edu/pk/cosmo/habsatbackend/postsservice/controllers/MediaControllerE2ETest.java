package pl.edu.pk.cosmo.habsatbackend.postsservice.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.pk.cosmo.habsatbackend.postsservice.E2ETest;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;
import pl.edu.pk.cosmo.habsatbackend.postsservice.repositories.MediaRepository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories.MediaEntityFactory;

import java.util.List;

import static org.assertj.db.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class MediaControllerE2ETest extends E2ETest {
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private AmazonS3 client;
    @Value("${cloud.aws.s3-bucket}")
    private String nameOfBucket;

    @Test
    public void shouldFindAllMediaReturnsAllMedia() throws Exception {
        List<MediaEntity> listOfMediaEntities = new MediaEntityFactory().createMany(2);
        mediaRepository.saveAll(listOfMediaEntities);

        mvc.perform(get(api("/media")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[1]").exists())
                .andExpect(jsonPath("$.[2]").doesNotExist());
    }

    @Test
    public void shouldFindMediaByIdReturnMedia() throws Exception {
        MediaEntity mediaEntity = mediaRepository.saveAndFlush(new MediaEntityFactory().create());

        mvc.perform(get(api("/media/" + mediaEntity.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mediaEntity.getId()))
                .andExpect(jsonPath("$.url").value(client.getUrl(nameOfBucket, mediaEntity.getS3Key()).toString()))
                .andExpect(jsonPath("$.nameOfFile").value(mediaEntity.getNameOfFile()))
                .andExpect(jsonPath("$.typeOfFile").value(mediaEntity.getTypeOfFile()))
                .andExpect(jsonPath("$.sizeOfFile").value(mediaEntity.getSizeOfFile()));
    }

    @Test
    public void shouldUploadMediaHandlesFileAndReturnMedia() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        MvcResult result = mvc.perform(multipart(api("/media")).file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.url").isString())
                .andExpect(jsonPath("$.nameOfFile").value(file.getOriginalFilename()))
                .andExpect(jsonPath("$.typeOfFile").value(file.getContentType()))
                .andExpect(jsonPath("$.sizeOfFile").value(file.getSize()))
                .andReturn();

        assertThat(table("media"))
                .hasNumberOfRows(1)
                .column("id").value().isNumber()
                .column("s3_key").value().isText()
                .column("name_of_file").value().isEqualTo(file.getOriginalFilename())
                .column("type_of_file").value().isEqualTo(file.getContentType())
                .column("size_of_file").value().isEqualTo(file.getSize());

        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        MediaEntity mediaEntity = mediaRepository.findById(json.getLong("id")).orElseThrow();
        assertThat(client.doesObjectExist(nameOfBucket, mediaEntity.getS3Key())).isTrue();
    }

    @Test
    public void shouldDeleteMediaDeletesMedia() throws Exception {
        MediaEntity mediaEntity = mediaRepository.saveAndFlush(new MediaEntityFactory().create());
        MockMultipartFile file = new MockMultipartFile("file", mediaEntity.getNameOfFile(), mediaEntity.getTypeOfFile(), "Hello, World!".getBytes());
        client.putObject(nameOfBucket, mediaEntity.getS3Key(), file.getInputStream(), new ObjectMetadata());

        mvc.perform(delete(api("/media/" + mediaEntity.getId())))
                .andExpect(status().isNoContent());

        assertThat(table("media")).hasNumberOfRows(0);
        assertThat(client.doesObjectExist(nameOfBucket, mediaEntity.getS3Key())).isFalse();
    }
}
