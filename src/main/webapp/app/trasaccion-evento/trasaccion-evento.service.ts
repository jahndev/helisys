import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { TrasaccionEventoDTO } from 'app/trasaccion-evento/trasaccion-evento.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class TrasaccionEventoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/trasaccionEventos';

  getAllTrasaccionEventoes() {
    return this.http.get<TrasaccionEventoDTO[]>(this.resourcePath);
  }

  getTrasaccionEvento(tvoId: number) {
    return this.http.get<TrasaccionEventoDTO>(this.resourcePath + '/' + tvoId);
  }

  createTrasaccionEvento(trasaccionEventoDTO: TrasaccionEventoDTO) {
    return this.http.post<number>(this.resourcePath, trasaccionEventoDTO);
  }

  updateTrasaccionEvento(tvoId: number, trasaccionEventoDTO: TrasaccionEventoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + tvoId, trasaccionEventoDTO);
  }

  deleteTrasaccionEvento(tvoId: number) {
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
