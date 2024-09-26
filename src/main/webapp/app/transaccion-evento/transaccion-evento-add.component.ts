import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { TransaccionEventoService } from 'app/transaccion-evento/transaccion-evento.service';
import { TransaccionEventoDTO } from 'app/transaccion-evento/transaccion-evento.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-transaccion-evento-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './transaccion-evento-add.component.html'
})
export class TransaccionEventoAddComponent implements OnInit {

  transaccionEventoService = inject(TransaccionEventoService);
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
      created: $localize`:@@transaccionEvento.create.success:Transaccion Evento was created successfully.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.transaccionEventoService.getTvoTteValues()
        .subscribe({
          next: (data) => this.tvoTteValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.transaccionEventoService.getAeronavesAnvValues()
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
    const data = new TransaccionEventoDTO(this.addForm.value);
    this.transaccionEventoService.createTransaccionEvento(data)
        .subscribe({
          next: () => this.router.navigate(['/transaccionEvento'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
