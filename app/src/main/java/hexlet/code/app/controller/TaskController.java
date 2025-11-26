package hexlet.code.app.controller;

import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.dto.task.TaskDTO;
import hexlet.code.app.dto.task.TaskParamsDTO;
import hexlet.code.app.dto.task.TaskUpdateDTO;
import hexlet.code.app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> indexFilter(
            TaskParamsDTO params,
            @RequestParam(defaultValue = "1") int pageCount,
            @RequestParam(defaultValue = "15") int pageSize) {

        var result = service.findAll(params, pageCount, pageSize);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO showTask(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    TaskDTO createTask(@Valid @RequestBody TaskCreateDTO taskData) {
        return service.create(taskData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userUtils.isAssignee(#id)")
    TaskDTO updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO dto) {
        return service.updateById(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.isAssignee(#id)")
    void deleteTask(@PathVariable Long id) {
        service.deleteById(id);
    }
}
