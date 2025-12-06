package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-06T12:05:53+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class TaskMapperImpl extends TaskMapper {

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    @Override
    public Task map(TaskCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Task task = new Task();

        task.setAssignee( idToUser( dto.getAssigneeId() ) );
        task.setName( dto.getTitle() );
        task.setDescription( dto.getContent() );
        task.setTaskStatus( slugToTaskStatus( dto.getStatus() ) );
        task.setLabelsUsed( labelIdsToLabels( dto.getTaskLabelIds() ) );
        task.setIndex( dto.getIndex() );

        return task;
    }

    @Override
    public TaskDTO map(Task model) {
        if ( model == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setAssigneeId( modelAssigneeId( model ) );
        taskDTO.setTitle( model.getName() );
        taskDTO.setContent( model.getDescription() );
        taskDTO.setStatus( modelTaskStatusSlug( model ) );
        taskDTO.setTaskLabelIds( labelsToLabelIds( model.getLabelsUsed() ) );
        taskDTO.setId( model.getId() );
        taskDTO.setIndex( model.getIndex() );
        taskDTO.setCreatedAt( model.getCreatedAt() );

        return taskDTO;
    }

    @Override
    public Task map(TaskDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Task task = new Task();

        task.setAssignee( idToUser( dto.getAssigneeId() ) );
        task.setName( dto.getTitle() );
        task.setDescription( dto.getContent() );
        task.setTaskStatus( slugToTaskStatus( dto.getStatus() ) );
        task.setLabelsUsed( labelIdsToLabels( dto.getTaskLabelIds() ) );
        task.setId( dto.getId() );
        task.setIndex( dto.getIndex() );
        task.setCreatedAt( dto.getCreatedAt() );

        return task;
    }

    @Override
    public void update(TaskUpdateDTO dto, Task model) {
        if ( dto == null ) {
            return;
        }

        if ( jsonNullableMapper.isPresent( dto.getAssigneeId() ) ) {
            model.setAssignee( idToUser( jsonNullableMapper.unwrap( dto.getAssigneeId() ) ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getTitle() ) ) {
            model.setName( jsonNullableMapper.unwrap( dto.getTitle() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getContent() ) ) {
            model.setDescription( jsonNullableMapper.unwrap( dto.getContent() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getStatus() ) ) {
            model.setTaskStatus( slugToTaskStatus( jsonNullableMapper.unwrap( dto.getStatus() ) ) );
        }
        if ( model.getLabelsUsed() != null ) {
            if ( jsonNullableMapper.isPresent( dto.getTaskLabelIds() ) ) {
                model.getLabelsUsed().clear();
                model.getLabelsUsed().addAll( labelIdsToLabels( jsonNullableMapper.unwrap( dto.getTaskLabelIds() ) ) );
            }
        }
        else {
            if ( jsonNullableMapper.isPresent( dto.getTaskLabelIds() ) ) {
                model.setLabelsUsed( labelIdsToLabels( jsonNullableMapper.unwrap( dto.getTaskLabelIds() ) ) );
            }
        }
        if ( jsonNullableMapper.isPresent( dto.getIndex() ) ) {
            model.setIndex( jsonNullableMapper.unwrap( dto.getIndex() ) );
        }
    }

    private Long modelAssigneeId(Task task) {
        if ( task == null ) {
            return null;
        }
        User assignee = task.getAssignee();
        if ( assignee == null ) {
            return null;
        }
        Long id = assignee.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String modelTaskStatusSlug(Task task) {
        if ( task == null ) {
            return null;
        }
        TaskStatus taskStatus = task.getTaskStatus();
        if ( taskStatus == null ) {
            return null;
        }
        String slug = taskStatus.getSlug();
        if ( slug == null ) {
            return null;
        }
        return slug;
    }
}
