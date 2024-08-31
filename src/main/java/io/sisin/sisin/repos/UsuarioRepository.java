package io.sisin.sisin.repos;

import io.sisin.sisin.domain.Escuadron;
import io.sisin.sisin.domain.Grado;
import io.sisin.sisin.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByUsrGdo(Grado grado);

    Usuario findFirstByUsrEdn(Escuadron escuadron);

    boolean existsByUsrCtIdentidad(Integer usrCtIdentidad);

    boolean existsByUsrCtMilitar(Integer usrCtMilitar);

    boolean existsByUsrLoginIgnoreCase(String usrLogin);

    boolean existsByUsrPasswordIgnoreCase(String usrPassword);

}
