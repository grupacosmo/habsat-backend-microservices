package pl.edu.pk.cosmo.habsatbackend.postsservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.cosmo.habsatbackend.postsservice.entities.MediaEntity;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    public List<MediaEntity> findAllByOrderById();
}
