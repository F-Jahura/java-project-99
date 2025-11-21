package hexlet.code.app.service.impl;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskParamsDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.service.TaskService;
import hexlet.code.app.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private TaskMapper mapper;
    @Autowired
    private TaskSpecification specBuilder;
/*
    @Override
    public List<TaskDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }*/

    @Override
    public List<TaskDTO> findAll(TaskParamsDTO params, int pageCount, int pageSize) {
        var spec = specBuilder.build(params);
        var pageParams = PageRequest.of(pageCount - 1, pageSize);
        return repository.findAll(spec, pageParams).map(mapper::map).toList();
    }

    @Override
    public TaskDTO findById(Long id) {

        var model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %d not found", id)));

        return mapper.map(model);
    }

    @Override
    public TaskDTO create(TaskCreateDTO dto) {
        var model = mapper.map(dto);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public TaskDTO updateById(TaskUpdateDTO dto, Long id) {

        var model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %d not found", id)));

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
