package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AeronaveDTO {

    private Integer anvId;

    @NotNull
    @Size(max = 45)
    @AeronaveAnvMatriculaUnique
    private String anvMatricula;

    @NotNull
    @Size(max = 45)
    private String anvNombre;

}
