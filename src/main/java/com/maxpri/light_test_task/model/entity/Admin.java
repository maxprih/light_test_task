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
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orgName;

    @Column(nullable = false)
    private String inn;

    @OneToMany(mappedBy = "admin")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "admin")
    private List<Event> events;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
