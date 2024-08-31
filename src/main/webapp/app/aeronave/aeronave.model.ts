export class AeronaveDTO {

  constructor(data:Partial<AeronaveDTO>) {
    Object.assign(this, data);
  }

  anvId?: number|null;
  anvMatricula?: string|null;
  anvNombre?: string|null;

}
