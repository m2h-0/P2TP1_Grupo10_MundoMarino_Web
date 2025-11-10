package com.grupo10.mundomarino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "guias") 
@Data
public class Guia extends Empleado {
    // Atributos específicos de guía
}

