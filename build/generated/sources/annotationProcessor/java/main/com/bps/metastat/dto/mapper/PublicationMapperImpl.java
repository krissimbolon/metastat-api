package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Publication;
import com.bps.metastat.dto.request.PublicationRequestDTO;
import com.bps.metastat.dto.response.PublicationResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T16:58:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PublicationMapperImpl implements PublicationMapper {

    @Override
    public Publication toEntity(PublicationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Publication.PublicationBuilder publication = Publication.builder();

        publication.title( dto.getTitle() );
        publication.isbn( dto.getIsbn() );
        publication.catalogNumber( dto.getCatalogNumber() );
        publication.releaseDate( dto.getReleaseDate() );
        publication.downloadUrl( dto.getDownloadUrl() );
        publication.type( dto.getType() );
        publication.language( dto.getLanguage() );

        return publication.build();
    }

    @Override
    public PublicationResponseDTO toResponseDTO(Publication publication) {
        if ( publication == null ) {
            return null;
        }

        PublicationResponseDTO.PublicationResponseDTOBuilder publicationResponseDTO = PublicationResponseDTO.builder();

        publicationResponseDTO.activity( toActivitySummary( publication.getActivity() ) );
        publicationResponseDTO.id( publication.getId() );
        publicationResponseDTO.title( publication.getTitle() );
        publicationResponseDTO.isbn( publication.getIsbn() );
        publicationResponseDTO.catalogNumber( publication.getCatalogNumber() );
        publicationResponseDTO.releaseDate( publication.getReleaseDate() );
        publicationResponseDTO.downloadUrl( publication.getDownloadUrl() );
        publicationResponseDTO.type( publication.getType() );
        publicationResponseDTO.language( publication.getLanguage() );
        publicationResponseDTO.createdAt( publication.getCreatedAt() );

        return publicationResponseDTO.build();
    }
}
