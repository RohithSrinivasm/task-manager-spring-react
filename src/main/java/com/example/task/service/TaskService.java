package com.example.task.service;

import com.example.task.dto.TaskDto;
import java.util.List;
import java.util.Map;

public interface TaskService {
    List<TaskDto> getAllTasks();
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Long id, TaskDto taskDto);
    TaskDto partiallyUpdateTask(Long id, Map<String, Object> updates);
    void deleteTask(Long id);
}
