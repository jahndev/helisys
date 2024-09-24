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
@Table(name = "Producto")
@Getter
@Setter
public class Producto {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proId;

    @Column(nullable = false, length = 45)
    private String proNumeroParte;

    @Column(nullable = false, length = 45)
    private String proNombre;

    @Column(unique = true, length = 45)
    private String proNumeroParteAlterno;

    @Column(unique = true, length = 45)
    private String proNumeroSerie;

    @Column(nullable = false)
    private Integer proUnidades;

    @Column(nullable = false)
    private LocalDate proFechaVencimiento;

    @Column
    private Integer proTipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_amr_id", nullable = false)
    private AlmacenRepisa proAmr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_tpo_ido_id", nullable = false)
    private TipoProducto proTpoIdo;

    @OneToMany(mappedBy = "tcoPro")
    private Set<TransaccionesProducto> tcoProTransaccionesProducto;

    @OneToMany(mappedBy = "pptPro")
    private Set<PedidosProducto> pptProPedidosProducto;

}
