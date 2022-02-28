package com.kodilla.demo.com.kodilla.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String status;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name="Task_Person",
        joinColumns = {@JoinColumn(name = "task_id")},
        inverseJoinColumns = {@JoinColumn(name = "person_id")}
    )
    private List<Person> responsible = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "Task_Subtask",
        joinColumns = {@JoinColumn(name="task_id")},
        inverseJoinColumns = {@JoinColumn(name = "subtask_id")}
    )
    private Set<Subtask> subtaskList = new HashSet<>();

    public Task(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public List<Person> getResponsible() {
        return responsible;
    }

    public Set<Subtask> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", responsible=" + responsible +
                ", subtaskList=" + subtaskList +
                '}';
    }
}
