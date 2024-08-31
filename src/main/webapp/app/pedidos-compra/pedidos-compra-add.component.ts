import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { PedidosCompraService } from 'app/pedidos-compra/pedidos-compra.service';
import { PedidosCompraDTO } from 'app/pedidos-compra/pedidos-compra.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-pedidos-compra-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './pedidos-compra-add.component.html'
})
export class PedidosCompraAddComponent implements OnInit {

  pedidosCompraService = inject(PedidosCompraService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  pcaUsrValues?: Map<number,string>;
  pcaPveValues?: Map<number,string>;

  addForm = new FormGroup({
    pcaDescripcion: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    pcaFechaPedido: new FormControl(null, [Validators.required]),
    pcaFechaEnvio: new FormControl(null, [Validators.required]),
    pcaFechaEntrega: new FormControl(null, [Validators.required]),
    pcaFechaPrometida: new FormControl(null, [Validators.required]),
    pcaDireccionEnvio: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    pcaUsr: new FormControl(null, [Validators.required]),
    pcaPve: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@pedidosCompra.create.success:Pedidos Compra was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.pedidosCompraService.getPcaUsrValues()
        .subscribe({
          next: (data) => this.pcaUsrValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.pedidosCompraService.getPcaPveValues()
        .subscribe({
          next: (data) => this.pcaPveValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new PedidosCompraDTO(this.addForm.value);
    this.pedidosCompraService.createPedidosCompra(data)
        .subscribe({
          next: () => this.router.navigate(['/pedidosCompras'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
