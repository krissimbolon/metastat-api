package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Domain;
import com.bps.metastat.domain.entity.Organization;
import com.bps.metastat.domain.entity.StatisticalActivity;
import com.bps.metastat.domain.entity.SubjectCategory;
import com.bps.metastat.domain.entity.User;
import com.bps.metastat.domain.enums.CollectionTechnique;
import com.bps.metastat.dto.request.ActivityRequestDTO;
import com.bps.metastat.dto.response.ActivityResponseDTO;
import com.bps.metastat.dto.response.DomainDTO;
import com.bps.metastat.dto.response.OrganizationDTO;
import com.bps.metastat.dto.response.SubjectDTO;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T16:58:30+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public StatisticalActivity toEntity(ActivityRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        StatisticalActivity.StatisticalActivityBuilder statisticalActivity = StatisticalActivity.builder();

        statisticalActivity.title( dto.getTitle() );
        statisticalActivity.year( dto.getYear() );
        statisticalActivity.description( dto.getDescription() );
        statisticalActivity.background( dto.getBackground() );
        statisticalActivity.objectives( dto.getObjectives() );
        statisticalActivity.collectionMethod( dto.getCollectionMethod() );
        statisticalActivity.frequency( dto.getFrequency() );
        Set<CollectionTechnique> set = dto.getCollectionTechniques();
        if ( set != null ) {
            statisticalActivity.collectionTechniques( new LinkedHashSet<CollectionTechnique>( set ) );
        }
        statisticalActivity.coverageArea( dto.getCoverageArea() );
        statisticalActivity.referencePeriod( dto.getReferencePeriod() );
        statisticalActivity.samplingMethod( dto.getSamplingMethod() );
        statisticalActivity.sampleSize( dto.getSampleSize() );
        statisticalActivity.isPublic( dto.getIsPublic() );
        statisticalActivity.releaseDate( dto.getReleaseDate() );

        return statisticalActivity.build();
    }

    @Override
    public ActivityResponseDTO toResponseDTO(StatisticalActivity activity) {
        if ( activity == null ) {
            return null;
        }

        ActivityResponseDTO.ActivityResponseDTOBuilder activityResponseDTO = ActivityResponseDTO.builder();

        activityResponseDTO.domain( toDomainDTO( activity.getDomain() ) );
        activityResponseDTO.organization( toOrganizationDTO( activity.getOrganization() ) );
        activityResponseDTO.subjects( subjectCategorySetToSubjectDTOSet( activity.getSubjects() ) );
        activityResponseDTO.createdBy( toUserInfo( activity.getCreatedBy() ) );
        activityResponseDTO.id( activity.getId() );
        activityResponseDTO.activityCode( activity.getActivityCode() );
        activityResponseDTO.title( activity.getTitle() );
        activityResponseDTO.year( activity.getYear() );
        activityResponseDTO.status( activity.getStatus() );
        activityResponseDTO.description( activity.getDescription() );
        activityResponseDTO.background( activity.getBackground() );
        activityResponseDTO.objectives( activity.getObjectives() );
        activityResponseDTO.collectionMethod( activity.getCollectionMethod() );
        activityResponseDTO.frequency( activity.getFrequency() );
        Set<CollectionTechnique> set1 = activity.getCollectionTechniques();
        if ( set1 != null ) {
            activityResponseDTO.collectionTechniques( new LinkedHashSet<CollectionTechnique>( set1 ) );
        }
        activityResponseDTO.coverageArea( activity.getCoverageArea() );
        activityResponseDTO.referencePeriod( activity.getReferencePeriod() );
        activityResponseDTO.samplingMethod( activity.getSamplingMethod() );
        activityResponseDTO.sampleSize( activity.getSampleSize() );
        activityResponseDTO.isPublic( activity.getIsPublic() );
        activityResponseDTO.releaseDate( activity.getReleaseDate() );
        activityResponseDTO.createdAt( activity.getCreatedAt() );
        activityResponseDTO.updatedAt( activity.getUpdatedAt() );

        activityResponseDTO.variableCount( activity.getVariables() != null ? activity.getVariables().size() : 0 );
        activityResponseDTO.publicationCount( activity.getPublications() != null ? activity.getPublications().size() : 0 );

        return activityResponseDTO.build();
    }

    @Override
    public DomainDTO toDomainDTO(Domain domain) {
        if ( domain == null ) {
            return null;
        }

        DomainDTO.DomainDTOBuilder domainDTO = DomainDTO.builder();

        domainDTO.id( domain.getId() );
        domainDTO.code( domain.getCode() );
        domainDTO.name( domain.getName() );
        domainDTO.type( domain.getType() );

        return domainDTO.build();
    }

    @Override
    public OrganizationDTO toOrganizationDTO(Organization organization) {
        if ( organization == null ) {
            return null;
        }

        OrganizationDTO.OrganizationDTOBuilder organizationDTO = OrganizationDTO.builder();

        organizationDTO.id( organization.getId() );
        organizationDTO.name( organization.getName() );
        organizationDTO.type( organization.getType() );

        return organizationDTO.build();
    }

    @Override
    public SubjectDTO toSubjectDTO(SubjectCategory subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectDTO.SubjectDTOBuilder subjectDTO = SubjectDTO.builder();

        subjectDTO.id( subject.getId() );
        subjectDTO.code( subject.getCode() );
        subjectDTO.name( subject.getName() );

        return subjectDTO.build();
    }

    @Override
    public ActivityResponseDTO.UserInfo toUserInfo(User user) {
        if ( user == null ) {
            return null;
        }

        ActivityResponseDTO.UserInfo.UserInfoBuilder userInfo = ActivityResponseDTO.UserInfo.builder();

        userInfo.id( user.getId() );
        userInfo.fullname( user.getFullname() );

        return userInfo.build();
    }

    @Override
    public void updateEntityFromDTO(ActivityRequestDTO dto, StatisticalActivity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getYear() != null ) {
            entity.setYear( dto.getYear() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getBackground() != null ) {
            entity.setBackground( dto.getBackground() );
        }
        if ( dto.getObjectives() != null ) {
            entity.setObjectives( dto.getObjectives() );
        }
        if ( dto.getCollectionMethod() != null ) {
            entity.setCollectionMethod( dto.getCollectionMethod() );
        }
        if ( dto.getFrequency() != null ) {
            entity.setFrequency( dto.getFrequency() );
        }
        if ( entity.getCollectionTechniques() != null ) {
            Set<CollectionTechnique> set = dto.getCollectionTechniques();
            if ( set != null ) {
                entity.getCollectionTechniques().clear();
                entity.getCollectionTechniques().addAll( set );
            }
        }
        else {
            Set<CollectionTechnique> set = dto.getCollectionTechniques();
            if ( set != null ) {
                entity.setCollectionTechniques( new LinkedHashSet<CollectionTechnique>( set ) );
            }
        }
        if ( dto.getCoverageArea() != null ) {
            entity.setCoverageArea( dto.getCoverageArea() );
        }
        if ( dto.getReferencePeriod() != null ) {
            entity.setReferencePeriod( dto.getReferencePeriod() );
        }
        if ( dto.getSamplingMethod() != null ) {
            entity.setSamplingMethod( dto.getSamplingMethod() );
        }
        if ( dto.getSampleSize() != null ) {
            entity.setSampleSize( dto.getSampleSize() );
        }
        if ( dto.getIsPublic() != null ) {
            entity.setIsPublic( dto.getIsPublic() );
        }
        if ( dto.getReleaseDate() != null ) {
            entity.setReleaseDate( dto.getReleaseDate() );
        }
    }

    protected Set<SubjectDTO> subjectCategorySetToSubjectDTOSet(Set<SubjectCategory> set) {
        if ( set == null ) {
            return null;
        }

        Set<SubjectDTO> set1 = new LinkedHashSet<SubjectDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SubjectCategory subjectCategory : set ) {
            set1.add( toSubjectDTO( subjectCategory ) );
        }

        return set1;
    }
}
