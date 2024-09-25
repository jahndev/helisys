package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransaccionTipoEventoDTO {

    private Integer tteId;

    @NotNull
    @Size(max = 45)
    private String tteNombre;

}
