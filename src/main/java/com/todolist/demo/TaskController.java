package com.todolist.demo;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TaskController {

    private final TaskRepository repository; //The Repository that stores all the tasks
    private final TaskModelAssembler assembler; //Assembles links to each individual task

    TaskController(TaskRepository repository, TaskModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/tasks")
    public CollectionModel<Task> all() {
        List<Task> tasks = (List<Task>) repository.findAll(); //A list of all the tasks

        //Takes each tasks and gives it a self relative link and a link relative to the link tasks
        for(Task task: tasks){
            task.add(assembler.toModel(task).getLinks()); 
        }

        //The link for the entire collection of tasks
        return CollectionModel.of(tasks, 
            linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> tasks(@RequestBody Task newTask) {

        EntityModel<Task> task = assembler.toModel(repository.save(newTask)); //Saves a new task and gives it it's link

        //Returns a status confirming creation of task and what the task is
        return ResponseEntity
        .created(task.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(task);
    }
    

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> one(@PathVariable("id") Long id) {
       Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException()); //Finds a task by id
       return assembler.toModel(task); //Returns the task with it's links
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteTask(@PathVariable("id") Long id){
        repository.deleteById(id); //Finds a task by id and deletes it 
        return ResponseEntity.noContent().build(); //Confirms deletion of task
    }
}
