package com.grupo10.mundomarino.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para manejar la información de las reservas de clientes (Rol Guía/Administrador)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {
    private int id;
    private String nombreCliente;
    private String fecha;
    private String hora;
    private int numVisitantes;

    @Override
    public String toString() {
        return "ID: " + id + 
               " | Cliente: " + nombreCliente + 
               " | Fecha: " + fecha + 
               " | Hora: " + hora + 
               " | Cantidad: " + numVisitantes;
    }
}