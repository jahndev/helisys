package io.sisin.sisin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "PedidosCompra")
@Getter
@Setter
public class PedidosCompra {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pcaId;

    @Column(nullable = false, length = 45)
    private String pcaDescripcion;

    @Column(nullable = false)
    private LocalDate pcaFechaPedido;

    @Column(nullable = false)
    private LocalDate pcaFechaEnvio;

    @Column(nullable = false)
    private LocalDate pcaFechaEntrega;

    @Column(nullable = false)
    private LocalDate pcaFechaPrometida;

    @Column(nullable = false, length = 45)
    private String pcaDireccionEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pca_usr_id", nullable = false)
    private Usuario pcaUsr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pca_pve_id", nullable = false)
    private Proveedor pcaPve;

    @OneToMany(mappedBy = "pptPca")
    private Set<PedidosProducto> pptPcaPedidosProducto;

}
