package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UnidadDTO {

    private Integer undId;

    @NotNull
    @Size(max = 45)
    private String undNombre;

    @NotNull
    @Size(max = 45)
    private String undTelefono;

    @NotNull
    @Size(max = 45)
    private String undFax;

    @NotNull
    @Size(max = 45)
    private String undComandanteNombre;

    @NotNull
    @Size(max = 200)
    private String undDireccion;

    @NotNull
    @Size(max = 45)
    private String undDepartamento;

    @NotNull
    @Size(max = 45)
    private String undProvincia;

    @NotNull
    @Size(max = 45)
    private String undCiudad;

    @NotNull
    private Integer undBga;

}
