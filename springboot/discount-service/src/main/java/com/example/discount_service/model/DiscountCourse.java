package com.example.discount_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "discount_course")
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class DiscountCourse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id", nullable = false)

    private Discount discount;

    @Column(name = "course_id", nullable = false)
    @NotNull(message = "Course can be not null")
    private Long courseId;
}
