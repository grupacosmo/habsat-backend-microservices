package pl.edu.pk.cosmo.habsatbackend.postsservice.utils.factories;

import java.util.List;

public interface EntityFactory<T> {
    public T create();

    public List<T> createMany(int count);
}
