package com.grupo10.mundomarino.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacto {

    @Id
    @Column(name = "dni", nullable = false)
    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 10000000, message = "El DNI debe tener al menos 8 dígitos")
    @Max(value = 999999999, message = "El DNI no puede tener más de 9 dígitos")
    private Integer dni;

    @Column(name = "nombres", nullable = false, length = 100)
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede tener más de 150 caracteres")
    private String correo;

    @Column(name = "foto", length = 255)
    private String foto;

    @PrePersist
    public void prePersist() {
        if (this.foto == null || this.foto.isEmpty()) {
            this.foto = "default.jpg";
        }
    }
}