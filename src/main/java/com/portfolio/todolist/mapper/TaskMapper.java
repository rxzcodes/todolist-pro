package com.portfolio.todolist.mapper;

import com.portfolio.todolist.dto.TaskRequestDTO;
import com.portfolio.todolist.dto.TaskResponseDTO;
import com.portfolio.todolist.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    // Converte DTO → Entidade (para criar/atualizar)
    public Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }

        return task;
    }

    // Converte Entidade → DTO (para resposta)
    public TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}