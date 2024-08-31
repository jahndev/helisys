import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { ContactoProveedorService } from 'app/contacto-proveedor/contacto-proveedor.service';
import { ContactoProveedorDTO } from 'app/contacto-proveedor/contacto-proveedor.model';


@Component({
  selector: 'app-contacto-proveedor-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './contacto-proveedor-list.component.html'})
export class ContactoProveedorListComponent implements OnInit, OnDestroy {

  contactoProveedorService = inject(ContactoProveedorService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  contactoProveedors?: ContactoProveedorDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@contactoProveedor.delete.success:Contacto Proveedor was removed successfully.`    };
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
    this.contactoProveedorService.getAllContactoProveedors()
        .subscribe({
          next: (data) => this.contactoProveedors = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(cveId: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.contactoProveedorService.deleteContactoProveedor(cveId)
          .subscribe({
            next: () => this.router.navigate(['/contactoProveedors'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => this.errorHandler.handleServerError(error.error)
          });
    }
  }

}
