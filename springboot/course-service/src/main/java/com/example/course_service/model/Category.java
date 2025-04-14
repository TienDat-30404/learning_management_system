package com.example.course_service.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper=true)

public class Category extends BaseEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
