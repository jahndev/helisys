export class TransaccionEventoDTO {

  constructor(data:Partial<TransaccionEventoDTO>) {
    Object.assign(this, data);
  }

  tvoId?: number|null;
  tvoFecha?: string|null;
  tvoTte?: number|null;
  aeronavesAnv?: number|null;

}
