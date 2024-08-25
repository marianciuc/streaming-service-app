package io.github.marianciuc.streamingservice.content.mappers;

import io.github.marianciuc.streamingservice.content.dto.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageMapper {
    public <E, D, F> PaginationResponse<D> mapEntityPageIntoDtoPage(Page<E> source, GenericMapper<E, D, F> mapper){
        List<D> dtos = source.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        return new PaginationResponse<>(
                dtos,
                source.getNumber(),
                source.getTotalElements(),
                source.getTotalPages()
        );
    }
}
