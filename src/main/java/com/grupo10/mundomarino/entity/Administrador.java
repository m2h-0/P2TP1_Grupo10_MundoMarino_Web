
package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "administradores")
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrador extends Empleado {
    // Atributos específicos de administrador si los hay
}
