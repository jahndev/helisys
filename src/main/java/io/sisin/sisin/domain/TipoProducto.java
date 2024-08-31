package io.sisin.sisin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "TipoProductoes")
@Getter
@Setter
public class TipoProducto {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tpoIdo;

    @Column(nullable = false, length = 45)
    private String tpoNombreTipo;

    @OneToMany(mappedBy = "proTpoIdo")
    private Set<Producto> proTpoIdoProductoes;

}
