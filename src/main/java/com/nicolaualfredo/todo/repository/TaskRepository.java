/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.todo.repository;

import com.nicolaualfredo.todo.model.Task;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicolaualfredo
 */
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 3;

    public TaskRepository() {
        tasks.add(new Task(1, "Estudar Java", "Estudar Java puro para projetos backend", false));
        tasks.add(new Task(2, "Criar API", "Construir API REST sem frameworks", false));
    }

    public List<Task> findAll() {
        return tasks;
    }

    public Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    public Task create(Task task) {
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }

    public boolean update(int id, Task updatedTask) {
        return findById(id).map(existing -> {
            existing.setTitle(updatedTask.getTitle());
            existing.setDescription(updatedTask.getDescription());
            existing.setCompleted(updatedTask.isCompleted());
            return true;
        }).orElse(false);
    }

    public boolean delete(int id) {
        return tasks.removeIf(t -> t.getId() == id);
    }
}
