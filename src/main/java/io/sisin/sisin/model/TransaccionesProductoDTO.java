package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransaccionesProductoDTO {

    private Integer tcoId;

    @NotNull
    private Integer tcoUnidades;

    @NotNull
    private Integer tcoTce;

    @NotNull
    private Integer tcoPro;

}
