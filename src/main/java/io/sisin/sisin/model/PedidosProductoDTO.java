package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PedidosProductoDTO {

    private Integer pptId;

    @NotNull
    private Integer pptCantidad;

    @NotNull
    private Integer pptPrecioUnitario;

    @NotNull
    private Integer pptPca;

    @NotNull
    private Integer pptPro;

}
