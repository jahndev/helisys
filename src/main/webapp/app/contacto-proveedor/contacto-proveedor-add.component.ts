import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ContactoProveedorService } from 'app/contacto-proveedor/contacto-proveedor.service';
import { ContactoProveedorDTO } from 'app/contacto-proveedor/contacto-proveedor.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';


@Component({
  selector: 'app-contacto-proveedor-add',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './contacto-proveedor-add.component.html'
})
export class ContactoProveedorAddComponent implements OnInit {

  contactoProveedorService = inject(ContactoProveedorService);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cpePveValues?: Map<number,string>;

  addForm = new FormGroup({
    cveNombre: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeTelefono: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeEmail: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeUrl: new FormControl(null, [Validators.required, Validators.maxLength(200)]),
    cpePve: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      created: $localize`:@@contactoProveedor.create.success:Contacto Proveedor was created successfully.`,
      CONTACTO_PROVEEDOR_CPE_EMAIL_UNIQUE: $localize`:@@Exists.contactoProveedor.cpeEmail:This Cpe Email is already taken.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.contactoProveedorService.getCpePveValues()
        .subscribe({
          next: (data) => this.cpePveValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
  }

  handleSubmit() {
    window.scrollTo(0, 0);
    this.addForm.markAllAsTouched();
    if (!this.addForm.valid) {
      return;
    }
    const data = new ContactoProveedorDTO(this.addForm.value);
    this.contactoProveedorService.createContactoProveedor(data)
        .subscribe({
          next: () => this.router.navigate(['/contactoProveedor'], {
            state: {
              msgSuccess: this.getMessage('created')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.addForm, this.getMessage)
        });
  }

}
