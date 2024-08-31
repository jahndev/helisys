package io.sisin.sisin.repos;

import io.sisin.sisin.domain.ContactoProveedor;
import io.sisin.sisin.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactoProveedorRepository extends JpaRepository<ContactoProveedor, Integer> {

    ContactoProveedor findFirstByCpePve(Proveedor proveedor);

    boolean existsByCpeEmailIgnoreCase(String cpeEmail);

}
