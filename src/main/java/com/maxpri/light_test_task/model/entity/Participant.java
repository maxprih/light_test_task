package com.maxpri.light_test_task.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author max_pri
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Boolean hasCovidTest;

    @ManyToMany(mappedBy = "participants")
    private List<Event> events;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
