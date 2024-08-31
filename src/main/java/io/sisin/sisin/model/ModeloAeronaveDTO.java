package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ModeloAeronaveDTO {

    private Integer mre;

    @NotNull
    @Size(max = 45)
    @ModeloAeronaveMreNombreUnique
    private String mreNombre;

    @NotNull
    private Integer mreAnv;

}
