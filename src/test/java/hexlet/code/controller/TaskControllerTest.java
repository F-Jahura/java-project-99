package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
//import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
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
    private LabelRepository labelRepository;
    @Autowired
    private TaskMapper taskMapper;
    private TaskStatus testStatus;
    private User testUser;
    private Label testLabel;
    private Task testTask;

    @BeforeEach
    public void setUp() {
        adminToken = jwt().jwt(builder -> builder.subject("hexlet@example.com"));

        testStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testStatus);

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        labelRepository.save(testLabel);

        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask.setTaskStatus(testStatus);
        testTask.setAssignee(testUser);
        testTask.setLabelsUsed(Set.of(testLabel));
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
    public void testCreate() throws Exception {
        var name = "Task Name";
        var content = "Task Content";
        Map<String, Object> data = new HashMap<>();
        data.put("title", name);
        data.put("content", content);
        data.put("status", testTask.getTaskStatus().getSlug());

        data.put("assignee_id", testUser.getId());
        data.put("taskLabelIds", Arrays.asList(testLabel.getId())); // список id меток
        //data.put("taskLabelIds", testTask.getLabels());

        var request = post("/api/tasks").with(adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        var result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                v -> v.node("id").isPresent(),
                v -> v.node("content").isPresent(),
                v -> v.node("title").isPresent(),
                v -> v.node("status").isEqualTo(data.get("status"))
                //v -> v.node("taskLabelIds").isEqualTo(testTask.getLabels())
        );
    }

/*
    @Test
    void testUpdate() throws Exception {
        var dto = new TaskUpdateDTO();
        dto.setTitle(JsonNullable.of("title-1"));
        dto.setContent(JsonNullable.of("content-1"));
        dto.setStatus(JsonNullable.of("published"));

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
