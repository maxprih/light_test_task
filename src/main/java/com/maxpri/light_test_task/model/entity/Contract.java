package com.maxpri.light_test_task.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max_pri
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String inn;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private ContractStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
}
