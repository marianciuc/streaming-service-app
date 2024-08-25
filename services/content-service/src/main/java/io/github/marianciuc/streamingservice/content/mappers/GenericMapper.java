package io.github.marianciuc.streamingservice.content.mappers;

/**
 * The GenericMapper interface represents a mapping contract between entity objects and DTO (Data Transfer Object) objects.
 * It provides methods to convert an entity object to a DTO object and vice versa.
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 * @param <F> the type used for converting the DTO back to the entity type
 */
public interface GenericMapper<E, D, F>{
    D toDto(E entity);
    E toEntity(F dto);
}
