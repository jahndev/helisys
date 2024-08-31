import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { PedidosCompraDTO } from 'app/pedidos-compra/pedidos-compra.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class PedidosCompraService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/pedidosCompras';

  getAllPedidosCompras() {
    return this.http.get<PedidosCompraDTO[]>(this.resourcePath);
  }

  getPedidosCompra(pcaId: number) {
    return this.http.get<PedidosCompraDTO>(this.resourcePath + '/' + pcaId);
  }

  createPedidosCompra(pedidosCompraDTO: PedidosCompraDTO) {
    return this.http.post<number>(this.resourcePath, pedidosCompraDTO);
  }

  updatePedidosCompra(pcaId: number, pedidosCompraDTO: PedidosCompraDTO) {
    return this.http.put<number>(this.resourcePath + '/' + pcaId, pedidosCompraDTO);
  }

  deletePedidosCompra(pcaId: number) {
    return this.http.delete(this.resourcePath + '/' + pcaId);
  }

  getPcaUsrValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/pcaUsrValues')
        .pipe(map(transformRecordToMap));
  }

  getPcaPveValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/pcaPveValues')
        .pipe(map(transformRecordToMap));
  }

}
