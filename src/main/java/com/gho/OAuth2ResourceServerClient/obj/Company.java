package com.gho.OAuth2ResourceServerClient.obj;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
//@Getter
//@Setter
//@ToString
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {

    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = true)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy="company")
    @JsonIgnoreProperties({"company" })
    @EqualsAndHashCode.Exclude
    private Set<Employee> employees;


}
