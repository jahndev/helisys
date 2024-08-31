import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ContactoProveedorDTO } from 'app/contacto-proveedor/contacto-proveedor.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ContactoProveedorService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/contactoProveedors';

  getAllContactoProveedors() {
    return this.http.get<ContactoProveedorDTO[]>(this.resourcePath);
  }

  getContactoProveedor(cveId: number) {
    return this.http.get<ContactoProveedorDTO>(this.resourcePath + '/' + cveId);
  }

  createContactoProveedor(contactoProveedorDTO: ContactoProveedorDTO) {
    return this.http.post<number>(this.resourcePath, contactoProveedorDTO);
  }

  updateContactoProveedor(cveId: number, contactoProveedorDTO: ContactoProveedorDTO) {
    return this.http.put<number>(this.resourcePath + '/' + cveId, contactoProveedorDTO);
  }

  deleteContactoProveedor(cveId: number) {
    return this.http.delete(this.resourcePath + '/' + cveId);
  }

  getCpePveValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/cpePveValues')
        .pipe(map(transformRecordToMap));
  }

}
