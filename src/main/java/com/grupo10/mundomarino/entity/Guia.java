package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "guias") 
@Data
@EqualsAndHashCode(callSuper = true)
public class Guia extends Empleado {
    // Atributos específicos de guía
}

