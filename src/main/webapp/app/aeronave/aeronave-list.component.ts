import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { AeronaveService } from 'app/aeronave/aeronave.service';
import { AeronaveDTO } from 'app/aeronave/aeronave.model';


@Component({
  selector: 'app-aeronave-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './aeronave-list.component.html'})
export class AeronaveListComponent implements OnInit, OnDestroy {

  aeronaveService = inject(AeronaveService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  aeronaves?: AeronaveDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@aeronave.delete.success:Aeronave was removed successfully.`,
      'aeronave.transaccionEvento.aeronavesAnv.referenced': $localize`:@@aeronave.transaccionEvento.aeronavesAnv.referenced:This entity is still referenced by Transaccion Evento ${details?.id} via field Aeronaves Anv.`,
      'aeronave.modeloAeronave.mreAnv.referenced': $localize`:@@aeronave.modeloAeronave.mreAnv.referenced:This entity is still referenced by Modelo Aeronave ${details?.id} via field Mre Anv.`
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
    this.aeronaveService.getAllAeronaves()
        .subscribe({
          next: (data) => this.aeronaves = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(anvId: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.aeronaveService.deleteAeronave(anvId)
          .subscribe({
            next: () => this.router.navigate(['/aeronaves'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/aeronaves'], {
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
