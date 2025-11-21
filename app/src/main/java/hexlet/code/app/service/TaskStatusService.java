package hexlet.code.app.service;

import hexlet.code.app.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusUpdateDTO;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatusDTO> findAll();
    TaskStatusDTO findById(Long id);
    TaskStatusDTO findBySlug(String slug);
    TaskStatusDTO create(TaskStatusCreateDTO dto);
    TaskStatusDTO updateById(TaskStatusUpdateDTO dto, Long id);
    void deleteById(Long id);
    boolean existsBySlug(String slug);
}
