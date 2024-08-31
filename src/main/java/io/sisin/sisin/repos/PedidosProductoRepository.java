package io.sisin.sisin.repos;

import io.sisin.sisin.domain.PedidosCompra;
import io.sisin.sisin.domain.PedidosProducto;
import io.sisin.sisin.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PedidosProductoRepository extends JpaRepository<PedidosProducto, Integer> {

    PedidosProducto findFirstByPptPca(PedidosCompra pedidosCompra);

    PedidosProducto findFirstByPptPro(Producto producto);

}
