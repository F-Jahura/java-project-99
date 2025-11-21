package hexlet.code.app.service.impl;

import hexlet.code.app.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusDTO;
import hexlet.code.app.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskStatusMapper;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    @Autowired
    private TaskStatusRepository repository;
    @Autowired
    private TaskStatusMapper mapper;

    @Override
    public List<TaskStatusDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public TaskStatusDTO findById(Long id) {

        var model = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Task status with id %d not found", id)));

        return mapper.map(model);
    }

    @Override
    public TaskStatusDTO findBySlug(String slug) {
        var model = repository.findBySlug(slug)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Task status with slug %s is not found", slug)));

        return mapper.map(model);
    }

    @Override
    public TaskStatusDTO create(TaskStatusCreateDTO dto) {
        var model = mapper.map(dto);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public TaskStatusDTO updateById(TaskStatusUpdateDTO dto, Long id) {

        var model = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Task status with id %d not found", id)));

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return false;
    }
}
