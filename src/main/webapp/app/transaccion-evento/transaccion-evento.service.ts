import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TransaccionEventoDTO } from 'app/transaccion-evento/transaccion-evento.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class TransaccionEventoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/transaccionEvento';

  getAllTransaccionEvento() {
    return this.http.get<TransaccionEventoDTO[]>(this.resourcePath);
  }

  getTransaccionEvento(tvoId: number) {
    return this.http.get<TransaccionEventoDTO>(this.resourcePath + '/' + tvoId);
  }

  createTransaccionEvento(transaccionEventoDTO: TransaccionEventoDTO) {
    return this.http.post<number>(this.resourcePath, transaccionEventoDTO);
  }

  updateTransaccionEvento(tvoId: number, transaccionEventoDTO: TransaccionEventoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + tvoId, transaccionEventoDTO);
  }

  deleteTransaccionEvento(tvoId: number) {
    return this.http.delete(this.resourcePath + '/' + tvoId);
  }

  getTvoTteValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/tvoTteValues')
        .pipe(map(transformRecordToMap));
  }

  getAeronavesAnvValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/aeronavesAnvValues')
        .pipe(map(transformRecordToMap));
  }

}
