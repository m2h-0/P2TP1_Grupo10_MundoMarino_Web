package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cuidadores")
@Data
public class Cuidador extends Empleado {
    // Atributos específicos de cuidador
}
