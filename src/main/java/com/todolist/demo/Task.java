package com.todolist.demo;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Task extends RepresentationModel<Task>{
    
    private @Id @GeneratedValue Long id;
    private String task;

    Task(){

    }

    Task(String task){
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(!(o instanceof Task)){
            return false;
        }

        Task t = (Task) o;
        return Objects.equals(this.getId(), t.getId()) && Objects.equals(this.getTask(), t.getTask());
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.task);
    }

    @Override
    public String toString(){
        return "Task{" + "id=" + this.id + ", task='" + this.task + '\'' + '}';  
    }
}
