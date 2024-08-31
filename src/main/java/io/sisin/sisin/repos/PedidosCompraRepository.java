package io.sisin.sisin.repos;

import io.sisin.sisin.domain.PedidosCompra;
import io.sisin.sisin.domain.Proveedor;
import io.sisin.sisin.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PedidosCompraRepository extends JpaRepository<PedidosCompra, Integer> {

    PedidosCompra findFirstByPcaUsr(Usuario usuario);

    PedidosCompra findFirstByPcaPve(Proveedor proveedor);

}
