package pl.edu.pk.cosmo.habsatbackend.postsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.PostEntity;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    public Boolean existsBySlug(String slug);
    public Boolean existsBySlugAndIdNot(String slug, Long id);
    public List<PostEntity> findAllByOrderById();
}
