package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BrigadaDTO {

    private Integer bgaId;

    @NotNull
    @Size(max = 45)
    private String bgaNombre;

}
