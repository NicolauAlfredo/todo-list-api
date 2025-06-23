/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicolaualfredo.todo.server;

import com.nicolaualfredo.todo.controller.TaskController;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author nicolaualfredo
 */
public class TodoServer {

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/tasks", new TaskController());
            server.setExecutor(null);
            server.start();
            System.out.println("Servidor rodando em http://localhost:8000/tasks");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
