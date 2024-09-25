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
@Table(name = "TransaccionEvento")
@Getter
@Setter
public class TransaccionEvento {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tvoId;

    @Column(nullable = false)
    private LocalDate tvoFecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tvo_tte_id", nullable = false)
    private TransaccionTipoEvento tvoTte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aeronaves_anv_id")
    private Aeronave aeronavesAnv;

    @OneToMany(mappedBy = "tceTvo")
    private Set<Transaccion> tceTvoTransaccion;

}
