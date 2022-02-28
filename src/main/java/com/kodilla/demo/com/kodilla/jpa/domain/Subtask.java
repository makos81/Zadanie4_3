package com.kodilla.demo.com.kodilla.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String status;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Subtask_Person",
            joinColumns = {@JoinColumn(name = "subtask_id")},
            inverseJoinColumns = {@JoinColumn(name = "person_id")}
    )
    private Set<Person> resposiblePerson = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Set<Person> getResposiblePerson() {
        return resposiblePerson;
    }

    public Subtask(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Subtask() {
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", resposiblePerson=" + resposiblePerson +
                '}';
    }
}
