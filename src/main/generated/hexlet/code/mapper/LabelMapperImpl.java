package hexlet.code.mapper;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.model.Label;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T12:05:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class LabelMapperImpl extends LabelMapper {

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    @Override
    public Label map(LabelCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Label label = new Label();

        label.setName( dto.getName() );

        return label;
    }

    @Override
    public LabelDTO map(Label model) {
        if ( model == null ) {
            return null;
        }

        LabelDTO labelDTO = new LabelDTO();

        labelDTO.setId( model.getId() );
        labelDTO.setName( model.getName() );
        labelDTO.setCreatedAt( model.getCreatedAt() );

        return labelDTO;
    }

    @Override
    public Label map(LabelDTO model) {
        if ( model == null ) {
            return null;
        }

        Label label = new Label();

        label.setId( model.getId() );
        label.setName( model.getName() );
        label.setCreatedAt( model.getCreatedAt() );

        return label;
    }

    @Override
    public void update(LabelUpdateDTO dto, Label model) {
        if ( dto == null ) {
            return;
        }

        if ( jsonNullableMapper.isPresent( dto.getName() ) ) {
            model.setName( jsonNullableMapper.unwrap( dto.getName() ) );
        }
    }
}
