package com.portfolio.todolist.dto;

import com.portfolio.todolist.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    private String title;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String description;

    private TaskStatus status;
}