package com.mv.streamingservice.content.mappers;

public interface GenericMapper<E, D, F>{
    D toDto(E entity);
    E toEntity(F dto);
}
