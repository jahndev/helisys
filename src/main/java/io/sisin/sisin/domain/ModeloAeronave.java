package io.sisin.sisin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ModeloAeronaves")
@Getter
@Setter
public class ModeloAeronave {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mre;

    @Column(nullable = false, unique = true, length = 45)
    private String mreNombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mre_anv_id", nullable = false)
    private Aeronave mreAnv;

}
