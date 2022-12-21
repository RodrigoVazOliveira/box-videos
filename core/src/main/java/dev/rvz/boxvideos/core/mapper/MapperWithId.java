package dev.rvz.boxvideos.core.mapper;

public interface MapperWithId<T, V> {
    V to(T to, Long id);
}
