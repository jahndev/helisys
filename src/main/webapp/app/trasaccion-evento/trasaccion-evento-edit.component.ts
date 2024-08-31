import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TrasaccionEventoService } from 'app/trasaccion-evento/trasaccion-evento.service';
import { TrasaccionEventoDTO } from 'app/trasaccion-evento/trasaccion-evento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-trasaccion-evento-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './trasaccion-evento-edit.component.html'
})
export class TrasaccionEventoEditComponent implements OnInit {

  trasaccionEventoService = inject(TrasaccionEventoService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  tvoTteValues?: Map<number,string>;
  aeronavesAnvValues?: Map<number,string>;
  currentTvoId?: number;

  editForm = new FormGroup({
    tvoId: new FormControl({ value: null, disabled: true }),
    tvoFecha: new FormControl(null, [Validators.required]),
    tvoTte: new FormControl(null, [Validators.required]),
    aeronavesAnv: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@trasaccionEvento.update.success:Trasaccion Evento was updated successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentTvoId = +this.route.snapshot.params['tvoId'];
    this.trasaccionEventoService.getTvoTteValues()
        .subscribe({
          next: (data) => this.tvoTteValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.trasaccionEventoService.getAeronavesAnvValues()
        .subscribe({
          next: (data) => this.aeronavesAnvValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.trasaccionEventoService.getTrasaccionEvento(this.currentTvoId!)
        .subscribe({
          next: (data) => updateForm(this.editForm, data),
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.editForm.markAllAsTouched();
    if (!this.editForm.valid) {
      return;
    }
    const data = new TrasaccionEventoDTO(this.editForm.value);
    this.trasaccionEventoService.updateTrasaccionEvento(this.currentTvoId!, data)
        .subscribe({
          next: () => this.router.navigate(['/trasaccionEventos'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
