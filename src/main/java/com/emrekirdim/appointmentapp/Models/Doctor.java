package com.emrekirdim.appointmentapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "doctors", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
