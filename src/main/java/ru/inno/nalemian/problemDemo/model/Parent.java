package ru.inno.nalemian.problemDemo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /**
     * FetchType.LAZY - fetch только когда требуется,
     * т.е. для каждого родителя, у которого не инициализирован лист children,
     * Hibernate выполняет отдельный SELECT к таблице child
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<>();

    public Parent(String name) {
        this.name = name;
    }
}