package com.grupo10.mundomarino.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import com.grupo10.mundomarino.entity.Empleado;
import java.util.List;

@Entity
@Table(name = "itinerarios")
@Data
public class Itinerario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itinerario")
    private Integer idItinerario;
    
    @Column(name = "desc_recorrido", length = 500)
    private String descRecorrido;
    
    private Integer longitud;
    
    @Column(name = "max_visitantes")
    private Integer maxVisitantes;
    
    @Column(name = "num_especies")
    private Integer numEspecies;

    @ManyToOne
    @JoinColumn(name = "id_empleado_guia")
    private Empleado guia;

    @OneToMany(mappedBy = "itinerario", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<ItinerarioEspecie> itinerarioEspecies;

    /**
     * Calcula el número de especies basado en la cantidad de registros en itinerarioEspecies.
     * Este método se usa para sincronizar numEspecies con la relación real.
     */
    public Integer calcularNumEspecies() {
        if (itinerarioEspecies == null || itinerarioEspecies.isEmpty()) {
            return 0;
        }
        return itinerarioEspecies.size();
    }
}