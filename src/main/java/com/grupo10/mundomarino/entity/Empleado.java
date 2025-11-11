package com.grupo10.mundomarino.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "empleados")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Empleado {

    @Id
    @Column(name = "id_empleado", length = 20)
    private String idEmpleado;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "tipo", nullable = false, length = 20, insertable = true)
    private String tipo;

    @Column(name = "usuario_id")
    private Long usuarioId;

// getter y setter (o usa Lombok @Data ya presente)
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @PrePersist
    public void prePersist() {
        if (this.fechaIngreso == null) {
            this.fechaIngreso = LocalDate.now();
        }
        if (this.tipo == null || this.tipo.isBlank()) {
            // fallback: usa el nombre de la clase solo si no vino del formulario
            this.tipo = this.getClass().getSimpleName().toUpperCase();
        }
    }
}
