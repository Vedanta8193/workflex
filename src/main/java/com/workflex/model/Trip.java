
package com.workflex.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip")  // optional but recommended
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`user`")
    private String user;

    private String destination;
}

