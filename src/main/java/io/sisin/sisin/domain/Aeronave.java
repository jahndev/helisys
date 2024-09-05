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
@Table(name = "Aeronave")
@Getter
@Setter
public class Aeronave {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer anvId;

    @Column(nullable = false, unique = true, length = 45)
    private String anvMatricula;

    @Column(nullable = false, length = 45)
    private String anvNombre;

    @OneToMany(mappedBy = "aeronavesAnv")
    private Set<TrasaccionEvento> aeronavesAnvTrasaccionEventoes;

    @OneToMany(mappedBy = "mreAnv")
    private Set<ModeloAeronave> mreAnvModeloAeronaves;

}
