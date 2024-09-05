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
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usrId;

    @Column(nullable = false, unique = true)
    private Integer usrCtIdentidad;

    @Column(nullable = false, unique = true)
    private Integer usrCtMilitar;

    @Column(nullable = false, length = 45)
    private String usrNombre;

    @Column(nullable = false, length = 45)
    private String usrApellido;

    @Column(nullable = false, length = 225)
    private String usrDireccion;

    @Column(nullable = false, length = 45)
    private String usrTelefono;

    @Column(nullable = false, length = 45)
    private String usrCargo;

    @Column(nullable = false, length = 225)
    private String usrFoto;

    @Column(nullable = false, unique = true, length = 45)
    private String usrLogin;

    @Column(nullable = false, unique = true, length = 45)
    private String usrPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_gdo_id", nullable = false)
    private Grado usrGdo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_edn_id", nullable = false)
    private Escuadron usrEdn;

    @OneToMany(mappedBy = "pcaUsr")
    private Set<PedidosCompra> pcaUsrPedidosCompras;

    @OneToMany(mappedBy = "tceUsr")
    private Set<Transaccion> tceUsrTransacciones;

}
