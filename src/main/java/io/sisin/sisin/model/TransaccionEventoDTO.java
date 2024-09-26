package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransaccionEventoDTO {

    private Integer tvoId;

    @NotNull
    private LocalDate tvoFecha;

    @NotNull
    private Integer tvoTte;

    private Integer aeronavesAnv;

}
