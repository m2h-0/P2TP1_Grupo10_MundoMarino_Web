package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cuidadores")
@Data
@EqualsAndHashCode(callSuper = true)
public class Cuidador extends Empleado {
    // Atributos específicos de cuidador
}
