package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoDTO {

    private Integer proId;

    @NotNull
    @Size(max = 45)
    private String proNumeroParte;

    @NotNull
    @Size(max = 45)
    private String proNombre;

    @Size(max = 45)
    @ProductoProNumeroParteAlternoUnique
    private String proNumeroParteAlterno;

    @Size(max = 45)
    @ProductoProNumeroSerieUnique
    private String proNumeroSerie;

    @NotNull
    private Integer proUnidades;

    @NotNull
    private LocalDate proFechaVencimiento;

    private Integer proTipoDocumento;

    @NotNull
    private Integer proAmr;

    @NotNull
    private Integer proTpoIdo;

}
