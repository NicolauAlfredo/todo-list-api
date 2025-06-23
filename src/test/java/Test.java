/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicolaualfredo
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolaualfredo.todo.model.Task;
import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class Test {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("data/tasks.json");
        List<Task> tasks = mapper.readValue(file, new TypeReference<List<Task>>() {
        });
        System.out.println(tasks);
    }
}
