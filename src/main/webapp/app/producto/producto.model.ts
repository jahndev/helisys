export class ProductoDTO {

  constructor(data:Partial<ProductoDTO>) {
    Object.assign(this, data);
  }

  proId?: number|null;
  proNumeroParte?: string|null;
  proNombre?: string|null;
  proNumeroParteAlterno?: string|null;
  proNumeroSerie?: string|null;
  proUnidades?: number|null;
  proFechaVencimiento?: string|null;
  proTipoDocumento?: number|null;
  proAmr?: number|null;
  proTpoIdo?: number|null;

}
