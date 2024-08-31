import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TransaccionDTO } from 'app/transaccion/transaccion.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class TransaccionService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/transaccions';

  getAllTransaccions() {
    return this.http.get<TransaccionDTO[]>(this.resourcePath);
  }

  getTransaccion(tceId: number) {
    return this.http.get<TransaccionDTO>(this.resourcePath + '/' + tceId);
  }

  createTransaccion(transaccionDTO: TransaccionDTO) {
    return this.http.post<number>(this.resourcePath, transaccionDTO);
  }

  updateTransaccion(tceId: number, transaccionDTO: TransaccionDTO) {
    return this.http.put<number>(this.resourcePath + '/' + tceId, transaccionDTO);
  }

  deleteTransaccion(tceId: number) {
    return this.http.delete(this.resourcePath + '/' + tceId);
  }

  getTceUsrValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/tceUsrValues')
        .pipe(map(transformRecordToMap));
  }

  getTceTvoValues() {
    return this.http.get<Record<string,number>>(this.resourcePath + '/tceTvoValues')
        .pipe(map(transformRecordToMap));
  }

}
