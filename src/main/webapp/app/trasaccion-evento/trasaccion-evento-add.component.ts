import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TrasaccionEventoService } from 'app/trasaccion-evento/trasaccion-evento.service';
import { TrasaccionEventoDTO } from 'app/trasaccion-evento/trasaccion-evento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-trasaccion-evento-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './trasaccion-evento-add.component.html'
})
export class TrasaccionEventoAddComponent implements OnInit {

  trasaccionEventoService = inject(TrasaccionEventoService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  tvoTteValues?: Map<number,string>;
  aeronavesAnvValues?: Map<number,string>;

  addForm = new FormGroup({
    tvoFecha: new FormControl(null, [Validators.required]),
    tvoTte: new FormControl(null, [Validators.required]),
    aeronavesAnv: new FormControl(null)
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@trasaccionEvento.create.success:Trasaccion Evento was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
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
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new TrasaccionEventoDTO(this.addForm.value);
    this.trasaccionEventoService.createTrasaccionEvento(data)
        .subscribe({
          next: () => this.router.navigate(['/trasaccionEventos'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
