package com.bps.metastat.dto.mapper;

import com.bps.metastat.domain.entity.Variable;
import com.bps.metastat.dto.request.VariableRequestDTO;
import com.bps.metastat.dto.response.VariableResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T16:58:29+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.1.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class VariableMapperImpl implements VariableMapper {

    @Override
    public Variable toEntity(VariableRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Variable.VariableBuilder variable = Variable.builder();

        variable.name( dto.getName() );
        variable.alias( dto.getAlias() );
        variable.definition( dto.getDefinition() );
        variable.questionText( dto.getQuestionText() );
        variable.dataType( dto.getDataType() );
        variable.referencePeriod( dto.getReferencePeriod() );

        return variable.build();
    }

    @Override
    public VariableResponseDTO toResponseDTO(Variable variable) {
        if ( variable == null ) {
            return null;
        }

        VariableResponseDTO.VariableResponseDTOBuilder variableResponseDTO = VariableResponseDTO.builder();

        variableResponseDTO.activity( toActivitySummary( variable.getActivity() ) );
        variableResponseDTO.id( variable.getId() );
        variableResponseDTO.name( variable.getName() );
        variableResponseDTO.alias( variable.getAlias() );
        variableResponseDTO.definition( variable.getDefinition() );
        variableResponseDTO.questionText( variable.getQuestionText() );
        variableResponseDTO.dataType( variable.getDataType() );
        variableResponseDTO.referencePeriod( variable.getReferencePeriod() );
        variableResponseDTO.createdAt( variable.getCreatedAt() );

        return variableResponseDTO.build();
    }
}
