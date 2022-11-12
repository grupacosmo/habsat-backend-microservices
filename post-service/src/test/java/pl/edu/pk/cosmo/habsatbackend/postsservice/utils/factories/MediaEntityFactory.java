package pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories;

import com.github.javafaker.Faker;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MediaEntityFactory implements EntityFactory<MediaEntity> {
    private final Faker faker = new Faker();

    @Override
    public MediaEntity create() {
        Long id = faker.number().randomNumber();
        String s3Key = faker.internet().slug();
        String nameOfFile = faker.file().fileName();
        String typeOfFile = faker.file().mimeType();
        Long sizeOfFile = faker.number().randomNumber();
        Date createdAt = faker.date().birthday();
        Date updatedAt = faker.date().birthday();

        return new MediaEntity(
                id,
                s3Key,
                nameOfFile,
                typeOfFile,
                sizeOfFile,
                createdAt,
                updatedAt
        );
    }

    @Override
    public List<MediaEntity> createMany(int count) {
        return Stream.generate(this::create).limit(count).collect(Collectors.toList());
    }

}
