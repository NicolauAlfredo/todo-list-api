/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.todo.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.todo.model.Task;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 * @author nicolaualfredo
 */
public class TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("data/tasks.json");
    private int nextId = 1;

    public TaskRepository() {
        loadFromFile();
        updateNextId();
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
        saveToFile();
        return task;
    }

    public boolean update(int id, Task updatedTask) {
        Optional<Task> existing = findById(id);
        if (existing.isPresent()) {
            Task t = existing.get();
            t.setTitle(updatedTask.getTitle());
            t.setDescription(updatedTask.getDescription());
            t.setCompleted(updatedTask.isCompleted());
            saveToFile();
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    private void loadFromFile() {
        try {
            if (file.exists()) {
                List<Task> loaded = mapper.readValue(file, new TypeReference<List<Task>>() {
                });
                tasks.clear();
                tasks.addAll(loaded);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar tasks.json: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, tasks);
        } catch (Exception e) {
            System.out.println("Erro ao salvar tasks.json: " + e.getMessage());
        }
    }

    private void updateNextId() {
        for (Task t : tasks) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }
    }
}
