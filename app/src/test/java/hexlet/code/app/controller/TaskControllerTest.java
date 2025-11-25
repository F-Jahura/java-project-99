package hexlet.code.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.dto.task.TaskCreateDTO;
import hexlet.code.app.mapper.TaskMapper;
import hexlet.code.app.model.Task;
import hexlet.code.app.model.TaskStatus;
import hexlet.code.app.model.User;
import hexlet.code.app.repository.TaskRepository;
import hexlet.code.app.repository.TaskStatusRepository;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request
        .SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"command.line.runner.enabled=false", "application.runner.enabled=false"})
@AutoConfigureMockMvc
@Transactional
@Rollback
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    private JwtRequestPostProcessor adminToken;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;
    private TaskStatus testStatus;
    private User testUser;
    private Task testTask;

    @BeforeEach
    public void setUp() {
        adminToken = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testStatus);

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);

        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask.setTaskStatus(testStatus);
        testTask.setAssignee(testUser);
        taskRepository.save(testTask);
    }

    @Test
    void showTest() throws Exception {
        var response = mockMvc.perform(get("/api/tasks/" + testTask.getId())
                        .with(adminToken))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThatJson(response.getContentAsString()).and(
                v -> v.node("assignee_id").isEqualTo(testUser.getId()),
                v -> v.node("status").isEqualTo(testStatus.getSlug())
        );
    }


    @Test
    void testCreate() throws Exception {
        taskRepository.delete(testTask);
        var dto = new TaskCreateDTO();
        dto.setTitle(testTask.getName());
        dto.setContent(testTask.getDescription());
        dto.setIndex(testTask.getIndex());
        dto.setAssigneeId(testTask.getAssignee().getId());
        dto.setStatus(testTask.getTaskStatus().getSlug());

        mockMvc.perform(post("/api/tasks")
                        .with(adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        var task = taskRepository.findByName(dto.getTitle()).orElseThrow();

        assertThat(task.getIndex()).isEqualTo(dto.getIndex());
        assertThat(task.getName()).isEqualTo(dto.getTitle());
        assertThat(task.getDescription()).isEqualTo(dto.getContent());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(dto.getStatus());
        assertThat(task.getAssignee().getId()).isEqualTo(dto.getAssigneeId());
    }
/*
    @Test
    void testUpdate() throws Exception {
        var dto = new TaskUpdateDTO();
        dto.setTitle(JsonNullable.of("some title"));
        dto.setContent(JsonNullable.of("some content"));
        dto.setStatus(JsonNullable.of("draft"));

        mockMvc.perform(put("/api/tasks/" + testTask.getId())
                        .with(adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk());

        var task = taskRepository.findByName(dto.getTitle().get()).orElseThrow();

        assertThat(task.getName()).isEqualTo(dto.getTitle().get());
        assertThat(task.getDescription()).isEqualTo(dto.getContent().get());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(dto.getStatus().get());
    }


   @Test
    void deleteTest() throws Exception {

        mockMvc.perform(delete("/api/tasks/" + testTask.getId())
                        .with(adminToken))
                .andExpect(status().isNoContent());

        assertThat(taskRepository.existsById(testTask.getId())).isFalse();
    }
    */

    @Test
    public void unauthorizedTest() throws Exception {

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/tasks/" + testTask.getId()))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(testTask)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(put("/api/tasks/" + testTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(testTask)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/api/tasks/" + testTask.getId()))
                .andExpect(status().isUnauthorized());
    }
}
