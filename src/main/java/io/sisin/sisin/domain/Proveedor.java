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
@Table(name = "Proveedor")
@Getter
@Setter
public class Proveedor {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pveId;

    @Column(nullable = false, length = 45)
    private String pveNombre;

    @Column(nullable = false, length = 45)
    private String pveTelefono;

    @Column(nullable = false, length = 45)
    private String pveFax;

    @Column(nullable = false, length = 45)
    private String pveEmail;

    @Column(nullable = false, length = 200)
    private String pveDireccion;

    @OneToMany(mappedBy = "pcaPve")
    private Set<PedidosCompra> pcaPvePedidosCompra;

    @OneToMany(mappedBy = "cpePve")
    private Set<ContactoProveedor> cpePveContactoProveedor;

}
