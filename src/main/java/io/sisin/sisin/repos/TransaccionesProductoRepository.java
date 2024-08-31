package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.domain.Transaccion;
import io.sisin.sisin.domain.TransaccionesProducto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionesProductoRepository extends JpaRepository<TransaccionesProducto, Integer> {

    TransaccionesProducto findFirstByTcoTce(Transaccion transaccion);

    TransaccionesProducto findFirstByTcoPro(Producto producto);

}
