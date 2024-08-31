package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlmacenRepisaDTO {

    private Integer amrId;

    @NotNull
    @Size(max = 200)
    private String amrDescripcion;

    @NotNull
    private Integer amrAmt;

}
