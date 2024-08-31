import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { TrasaccionEventoService } from 'app/trasaccion-evento/trasaccion-evento.service';
import { TrasaccionEventoDTO } from 'app/trasaccion-evento/trasaccion-evento.model';


@Component({
  selector: 'app-trasaccion-evento-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './trasaccion-evento-list.component.html'})
export class TrasaccionEventoListComponent implements OnInit, OnDestroy {

  trasaccionEventoService = inject(TrasaccionEventoService);
  errorHandler = inject(ErrorHandler);
  router = inject(Router);
  trasaccionEventoes?: TrasaccionEventoDTO[];
  navigationSubscription?: Subscription;

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      confirm: $localize`:@@delete.confirm:Do you really want to delete this element? This cannot be undone.`,
      deleted: $localize`:@@trasaccionEvento.delete.success:Trasaccion Evento was removed successfully.`,
      'trasaccionEvento.transaccion.tceTvo.referenced': $localize`:@@trasaccionEvento.transaccion.tceTvo.referenced:This entity is still referenced by Transaccion ${details?.id} via field Tce Tvo.`
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
    this.trasaccionEventoService.getAllTrasaccionEventoes()
        .subscribe({
          next: (data) => this.trasaccionEventoes = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  confirmDelete(tvoId: number) {
    if (confirm(this.getMessage('confirm'))) {
      this.trasaccionEventoService.deleteTrasaccionEvento(tvoId)
          .subscribe({
            next: () => this.router.navigate(['/trasaccionEventos'], {
              state: {
                msgInfo: this.getMessage('deleted')
              }
            }),
            error: (error) => {
              if (error.error?.code === 'REFERENCED') {
                const messageParts = error.error.message.split(',');
                this.router.navigate(['/trasaccionEventos'], {
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
