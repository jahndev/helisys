package io.sisin.sisin.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactoProveedorDTO {

    private Integer cveId;

    @NotNull
    @Size(max = 45)
    private String cveNombre;

    @NotNull
    @Size(max = 45)
    private String cpeTelefono;

    @NotNull
    @Size(max = 45)
    @ContactoProveedorCpeEmailUnique
    private String cpeEmail;

    @NotNull
    @Size(max = 200)
    private String cpeUrl;

    @NotNull
    private Integer cpePve;

}
