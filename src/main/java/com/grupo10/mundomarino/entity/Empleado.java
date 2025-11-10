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
    
    @Column(name = "tipo", nullable = false, length = 20, updatable = false, insertable = true)
    private String tipo;

    @PrePersist
    public void asignarTipo() {
        this.tipo = this.getClass().getSimpleName().toUpperCase(); // "GUIA", "CUIDADOR", "ADMINISTRADOR"
    }
}
