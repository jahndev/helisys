import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { TransaccionService } from 'app/transaccion/transaccion.service';
import { TransaccionDTO } from 'app/transaccion/transaccion.model';


@Component({
  selector: 'app-transaccion-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './transaccion-list.component.html'})
export class TransaccionListComponent implements OnInit, OnDestroy {

  transaccionService = inject(TransaccionService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  transaccion?: TransaccionDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@transaccion.delete.success:Transaccion was removed successfully.`,
      'transaccion.transaccionesProducto.tcoTce.referenced': $localize`:@@transaccion.transaccionesProducto.tcoTce.referenced:This entity is still referenced by Transacciones Producto ${details?.id} via field Tco Tce.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.loadData();
    this.navigationSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.loadData();
      }
    });
  }

  ngOnDestroy() {
    this.navigationSubscription!.unsubscribe();
  }

  loadData() {
    this.transaccionService.getAllTransaccion()
        .subscribe({
          next: (data) => this.transaccion = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(tceId: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.transaccionService.deleteTransaccion(tceId)
          .subscribe({
            next: () => this.router.navigate(['/transaccion'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/transaccion'], {
                  state: {
                    msgError: this.getMessage(messageParts[0], { id: messageParts[1] })
                  }
                });
                return;
              }
              this.errorHandler.handleServerError(error.error)
            }
          });
    }
  }

}
