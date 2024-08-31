export class TrasaccionEventoDTO {

  constructor(data:Partial<TrasaccionEventoDTO>) {
    Object.assign(this, data);
  }

  tvoId?: number|null;
  tvoFecha?: string|null;
  tvoTte?: number|null;
  aeronavesAnv?: number|null;

}
