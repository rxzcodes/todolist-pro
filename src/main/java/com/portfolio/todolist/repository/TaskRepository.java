package com.portfolio.todolist.repository;

import com.portfolio.todolist.model.Task;
import com.portfolio.todolist.model.TaskStatus;
import com.portfolio.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // NOVOS: Buscar por usuário
    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String title);

    Optional<Task> findByIdAndUser(Long id, User user);

    // Antigos (podem remover se quiser)
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String title);
}