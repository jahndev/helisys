export class ContactoProveedorDTO {

  constructor(data:Partial<ContactoProveedorDTO>) {
    Object.assign(this, data);
  }

  cveId?: number|null;
  cveNombre?: string|null;
  cpeTelefono?: string|null;
  cpeEmail?: string|null;
  cpeUrl?: string|null;
  cpePve?: number|null;

}
