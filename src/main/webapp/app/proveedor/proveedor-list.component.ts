import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ProveedorService } from 'app/proveedor/proveedor.service';
import { ProveedorDTO } from 'app/proveedor/proveedor.model';


@Component({
  selector: 'app-proveedor-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './proveedor-list.component.html'})
export class ProveedorListComponent implements OnInit, OnDestroy {

  proveedorService = inject(ProveedorService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  proveedors?: ProveedorDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@proveedor.delete.success:Proveedor was removed successfully.`,
      'proveedor.pedidosCompra.pcaPve.referenced': $localize`:@@proveedor.pedidosCompra.pcaPve.referenced:This entity is still referenced by Pedidos Compra ${details?.id} via field Pca Pve.`,
      'proveedor.contactoProveedor.cpePve.referenced': $localize`:@@proveedor.contactoProveedor.cpePve.referenced:This entity is still referenced by Contacto Proveedor ${details?.id} via field Cpe Pve.`
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
    this.proveedorService.getAllProveedors()
        .subscribe({
          next: (data) => this.proveedors = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(pveId: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.proveedorService.deleteProveedor(pveId)
          .subscribe({
            next: () => this.router.navigate(['/proveedors'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/proveedors'], {
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
