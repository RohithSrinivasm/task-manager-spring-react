package com.example.task.service;

import com.example.task.dto.TaskDto;
import com.example.task.model.Task;
import com.example.task.repo.TaskRepository;
import com.example.task.service.TaskService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private static final Long FIXED_USER_ID = 1L;

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        return dto;
    }

    private Task mapToEntity(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setUserId(FIXED_USER_ID); // always set userId to 1
        return task;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findByUserId(FIXED_USER_ID).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .filter(t -> t.getUserId().equals(FIXED_USER_ID))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return mapToDto(task);
    }

    @Override
    public TaskDto createTask(TaskDto dto) {
        Task task = mapToEntity(dto);
        return mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());

        return mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto partiallyUpdateTask(Long id, Map<String, Object> updates) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title" -> task.setTitle((String) value);
                case "description" -> task.setDescription((String) value);
                case "dueDate" -> task.setDueDate(LocalDate.parse((String) value));
            }
        });

        return mapToDto(taskRepository.save(task));
    }


    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
