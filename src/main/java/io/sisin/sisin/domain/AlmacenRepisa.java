package io.sisin.sisin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "AlmacenRepisa")
@Getter
@Setter
public class AlmacenRepisa {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer amrId;

    @Column(nullable = false, length = 200)
    private String amrDescripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amr_amt_id", nullable = false)
    private AlmacenEstante amrAmt;

    @OneToMany(mappedBy = "proAmr")
    private Set<Producto> proAmrProducto;

}
