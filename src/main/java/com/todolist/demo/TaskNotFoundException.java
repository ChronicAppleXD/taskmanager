package com.todolist.demo;

public class TaskNotFoundException extends RuntimeException{
    TaskNotFoundException(){
        super("Task Not Found");
    }
    TaskNotFoundException(Long id){
        super("Task " + id + " Not Found");
    }
}
