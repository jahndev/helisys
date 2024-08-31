package io.sisin.sisin.repos;

import io.sisin.sisin.domain.AlmacenRepisa;
import io.sisin.sisin.domain.Producto;
import io.sisin.sisin.domain.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Producto findFirstByProAmr(AlmacenRepisa almacenRepisa);

    Producto findFirstByProTpoIdo(TipoProducto tipoProducto);

    boolean existsByProNumeroParteAlternoIgnoreCase(String proNumeroParteAlterno);

    boolean existsByProNumeroSerieIgnoreCase(String proNumeroSerie);

}
