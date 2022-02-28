package com.kodilla.demo;

import com.kodilla.demo.com.kodilla.jpa.domain.Person;
import com.kodilla.demo.com.kodilla.jpa.domain.Subtask;
import com.kodilla.demo.com.kodilla.jpa.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class TaskTestSuite {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldNPlusOneProblemOccur() {
        List<Long> insertedTasks = insertTask();
        EntityManager em = entityManagerFactory.createEntityManager();

        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for task ***");

        List<Task> taskList = em.createQuery(
                "from Task where id in (" + taskIds(insertedTasks) + ")",
                Task.class).getResultList();

        for (Task task: taskList){
            System.out.println("*** STEP 1b – query for task ***");
            System.out.println(task);
            for(Person person: task.getResponsible()){
                System.out.println("*** STEP 2 – read data from reponsible persons of task ***");
                System.out.println(person);
            }
            for(Subtask subtask:task.getSubtaskList()){
                System.out.println("*** STEP 3 – read data from subtask ***");
                System.out.println(subtask);
                for(Person person: subtask.getResposiblePerson()){
                    System.out.println("*** STEP 4 – read data from reponsible persons of subtask ***");
                    System.out.println(person);
                }
            }
        }
    }

    @Test
    void withEntityGraph() {
        List<Long> insertedTasks = insertTask();
        EntityManager em = entityManagerFactory.createEntityManager();

        System.out.println("****************** BEGIN OF FETCHING *******************");

        TypedQuery<Task> query = em.createQuery(
                "from Task where id in (" + taskIds(insertedTasks) + ")",
                Task.class);

        EntityGraph<Task> eg = em.createEntityGraph(Task.class);
        eg.addSubgraph("responsible");
        eg.addSubgraph("subtaskList");
        query.setHint("javax.persistence.fetchgraph", eg);

        System.out.println("*** STEP 1 – query for tasks ***");
        List<Task> taskList =
                query.getResultList();

        for (Task task: taskList){
            System.out.println("*** STEP 1b – query for task ***");
            System.out.println(task);
            for(Person person: task.getResponsible()){
                System.out.println("*** STEP 2 – read data from reponsible persons of task ***");
                System.out.println(person);
            }
            for(Subtask subtask:task.getSubtaskList()){
                System.out.println("*** STEP 3 – read data from subtask ***");
                System.out.println(subtask);
                for(Person person: subtask.getResposiblePerson()){
                    System.out.println("*** STEP 4 – read data from reponsible persons of subtask ***");
                    System.out.println(person);
                }
            }
        }
    }

    private String taskIds(List<Long> list){
        return list.stream().map(e->"" + e).collect(Collectors.joining(","));
    }

    private List<Long> insertTask(){
        Person personA = new Person(null,"nameA", "nameB");
        Person personB = new Person(null,"nameB", "surnameB");
        Person personC = new Person(null,"nameC", "nameC");
        Person personD = new Person(null,"nameD", "surnameD");
        Subtask subtask1 = new Subtask(null, "subtask1", "in progress");
        subtask1.getResposiblePerson().addAll(List.of(personA, personB));
        Subtask subtask2 = new Subtask(null, "subtask2", "done");
        subtask2.getResposiblePerson().addAll(List.of(personA, personB));
        Task taskA = new Task(null, "taskA", "ok");
        taskA.getResponsible().addAll(List.of(personC));
        taskA.getSubtaskList().add(subtask1);
        Task taskB = new Task(null, "taskB", "ok");
        taskB.getResponsible().addAll(List.of(personD));
        taskB.getSubtaskList().add(subtask2);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(personA);
        em.persist(personB);
        em.persist(personC);
        em.persist(personD);
        em.persist(subtask1);
        em.persist(subtask2);
        em.persist(taskA);
        em.persist(taskB);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(taskA.getId(), taskB.getId());
    }
}
