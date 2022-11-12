package pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories;

import com.github.javafaker.Faker;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostEntityFactory implements EntityFactory<PostEntity> {
    private final Faker faker = new Faker();

    @Override
    public PostEntity create() {
        Long id = faker.number().randomNumber();
        String title = faker.book().title();
        String slug = faker.internet().slug();
        String content = faker.lorem().sentence(30);
        String emailOfAuthor = faker.internet().emailAddress();
        Date publishedAt = faker.date().birthday();
        Date createdAt = faker.date().birthday();
        Date updatedAt = faker.date().birthday();

        return new PostEntity(
                id,
                null,
                title,
                slug,
                content,
                emailOfAuthor,
                publishedAt,
                createdAt,
                updatedAt,
                null
        );
    }

    @Override
    public List<PostEntity> createMany(int count) {
        return Stream.generate(this::create).limit(count).collect(Collectors.toList());
    }

}
