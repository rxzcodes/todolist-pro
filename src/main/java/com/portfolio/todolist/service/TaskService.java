package com.portfolio.todolist.service;

import com.portfolio.todolist.dto.TaskRequestDTO;
import com.portfolio.todolist.dto.TaskResponseDTO;
import com.portfolio.todolist.mapper.TaskMapper;
import com.portfolio.todolist.model.Task;
import com.portfolio.todolist.model.TaskStatus;
import com.portfolio.todolist.model.User;
import com.portfolio.todolist.repository.TaskRepository;
import com.portfolio.todolist.repository.UserRepository;
import com.portfolio.todolist.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    // Método auxiliar para pegar o usuário autenticado
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    // Criar uma nova tarefa
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        User user = getAuthenticatedUser();

        Task task = taskMapper.toEntity(requestDTO);
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user); // Associa a tarefa ao usuário autenticado

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(savedTask);
    }

    // Listar todas as tarefas DO USUÁRIO AUTENTICADO
    public List<TaskResponseDTO> getAllTasks() {
        User user = getAuthenticatedUser();

        return taskRepository.findByUser(user).stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar tarefa por ID (apenas se pertencer ao usuário)
    public TaskResponseDTO getTaskById(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        return taskMapper.toResponseDTO(task);
    }

    // Buscar tarefas por status (do usuário autenticado)
    public List<TaskResponseDTO> getTasksByStatus(TaskStatus status) {
        User user = getAuthenticatedUser();

        return taskRepository.findByUserAndStatus(user, status).stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar tarefas por título (do usuário autenticado)
    public List<TaskResponseDTO> searchTasksByTitle(String title) {
        User user = getAuthenticatedUser();

        return taskRepository.findByUserAndTitleContainingIgnoreCase(user, title).stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Atualizar uma tarefa (apenas se pertencer ao usuário)
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());

        if (requestDTO.getStatus() != null) {
            task.setStatus(requestDTO.getStatus());
        }

        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponseDTO(updatedTask);
    }

    // Deletar uma tarefa (apenas se pertencer ao usuário)
    public void deleteTask(Long id) {
        User user = getAuthenticatedUser();

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        taskRepository.delete(task);
    }
}