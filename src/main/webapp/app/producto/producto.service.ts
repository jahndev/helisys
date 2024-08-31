import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'environments/environment';
import { ProductoDTO } from 'app/producto/producto.model';
import { map } from 'rxjs';
import { transformRecordToMap } from 'app/common/utils';


@Injectable({
  providedIn: 'root',
})
export class ProductoService {

  http = inject(HttpClient);
  resourcePath = environment.apiPath + '/api/productos';

  getAllProductoes() {
    return this.http.get<ProductoDTO[]>(this.resourcePath);
  }

  getProducto(proId: number) {
    return this.http.get<ProductoDTO>(this.resourcePath + '/' + proId);
  }

  createProducto(productoDTO: ProductoDTO) {
    return this.http.post<number>(this.resourcePath, productoDTO);
  }

  updateProducto(proId: number, productoDTO: ProductoDTO) {
    return this.http.put<number>(this.resourcePath + '/' + proId, productoDTO);
  }

  deleteProducto(proId: number) {
    return this.http.delete(this.resourcePath + '/' + proId);
  }

  getProAmrValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/proAmrValues')
        .pipe(map(transformRecordToMap));
  }

  getProTpoIdoValues() {
    return this.http.get<Record<string,string>>(this.resourcePath + '/proTpoIdoValues')
        .pipe(map(transformRecordToMap));
  }

}
