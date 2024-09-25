import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { InputRowComponent } from 'app/common/input-row/input-row.component';
import { ContactoProveedorService } from 'app/contacto-proveedor/contacto-proveedor.service';
import { ContactoProveedorDTO } from 'app/contacto-proveedor/contacto-proveedor.model';
import { ErrorHandler } from 'app/common/error-handler.injectable';
import { updateForm } from 'app/common/utils';


@Component({
  selector: 'app-contacto-proveedor-edit',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, InputRowComponent],
  templateUrl: './contacto-proveedor-edit.component.html'
})
export class ContactoProveedorEditComponent implements OnInit {

  contactoProveedorService = inject(ContactoProveedorService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  errorHandler = inject(ErrorHandler);

  cpePveValues?: Map<number,string>;
  currentCveId?: number;

  editForm = new FormGroup({
    cveId: new FormControl({ value: null, disabled: true }),
    cveNombre: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeTelefono: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeEmail: new FormControl(null, [Validators.required, Validators.maxLength(45)]),
    cpeUrl: new FormControl(null, [Validators.required, Validators.maxLength(200)]),
    cpePve: new FormControl(null, [Validators.required])
  }, { updateOn: 'submit' });

  getMessage(key: string, details?: any) {
    const messages: Record<string, string> = {
      updated: $localize`:@@contactoProveedor.update.success:Contacto Proveedor was updated successfully.`,
      CONTACTO_PROVEEDOR_CPE_EMAIL_UNIQUE: $localize`:@@Exists.contactoProveedor.cpeEmail:This Cpe Email is already taken.`
    };
    return messages[key];
  }

  ngOnInit() {
    this.currentCveId = +this.route.snapshot.params['cveId'];
    this.contactoProveedorService.getCpePveValues()
        .subscribe({
          next: (data) => this.cpePveValues = data,
          error: (error) => this.errorHandler.handleServerError(error.error)
        });
    this.contactoProveedorService.getContactoProveedor(this.currentCveId!)
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
    const data = new ContactoProveedorDTO(this.editForm.value);
    this.contactoProveedorService.updateContactoProveedor(this.currentCveId!, data)
        .subscribe({
          next: () => this.router.navigate(['/contactoProveedor'], {
            state: {
              msgSuccess: this.getMessage('updated')
            }
          }),
          error: (error) => this.errorHandler.handleServerError(error.error, this.editForm, this.getMessage)
        });
  }

}
