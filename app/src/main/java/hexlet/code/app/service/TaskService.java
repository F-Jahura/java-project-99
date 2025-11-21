package hexlet.code.app.service;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskParamsDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;

import java.util.List;

public interface TaskService {
    //List<TaskDTO> findAll();
    List<TaskDTO> findAll(TaskParamsDTO params, int pageCount, int pageSize);
    TaskDTO findById(Long id);
    TaskDTO create(TaskCreateDTO dto);
    TaskDTO updateById(TaskUpdateDTO dto, Long id);
    void deleteById(Long id);
}
