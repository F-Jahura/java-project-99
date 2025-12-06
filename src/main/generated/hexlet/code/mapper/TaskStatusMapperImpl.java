package hexlet.code.mapper;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.model.TaskStatus;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T12:05:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class TaskStatusMapperImpl extends TaskStatusMapper {

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    @Override
    public TaskStatus map(TaskStatusCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setName( dto.getName() );
        taskStatus.setSlug( dto.getSlug() );

        return taskStatus;
    }

    @Override
    public TaskStatusDTO map(TaskStatus model) {
        if ( model == null ) {
            return null;
        }

        TaskStatusDTO taskStatusDTO = new TaskStatusDTO();

        taskStatusDTO.setId( model.getId() );
        taskStatusDTO.setName( model.getName() );
        taskStatusDTO.setSlug( model.getSlug() );
        taskStatusDTO.setCreatedAt( model.getCreatedAt() );

        return taskStatusDTO;
    }

    @Override
    public TaskStatus map(TaskStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TaskStatus taskStatus = new TaskStatus();

        taskStatus.setId( dto.getId() );
        taskStatus.setName( dto.getName() );
        taskStatus.setSlug( dto.getSlug() );
        taskStatus.setCreatedAt( dto.getCreatedAt() );

        return taskStatus;
    }

    @Override
    public void update(TaskStatusUpdateDTO dto, TaskStatus model) {
        if ( dto == null ) {
            return;
        }

        if ( jsonNullableMapper.isPresent( dto.getName() ) ) {
            model.setName( jsonNullableMapper.unwrap( dto.getName() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getSlug() ) ) {
            model.setSlug( jsonNullableMapper.unwrap( dto.getSlug() ) );
        }
    }
}
