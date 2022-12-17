package dev.rvz.boxvideos.core.mapper;

public interface Mapper<T, V> {
    V to(T to);
}
