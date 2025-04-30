package ru.inno.nalemian.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Account> accounts;

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "'}";
    }
}
