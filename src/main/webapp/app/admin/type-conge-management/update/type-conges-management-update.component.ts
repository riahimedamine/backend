import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ITypeConge } from '../type-conge-management.model';
import { TypeManagementService } from '../service/type-conges-management.service';

const typeTemplate = {} as ITypeConge;

const newType: ITypeConge = {
  isDeleted: false,
} as ITypeConge;

@Component({
  selector: 'jhi-type-conges-mgmt-update',
  templateUrl: './type-conges-management-update.component.html',
})
export class TypeCongesManagementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = new FormGroup({
    id: new FormControl(typeTemplate.id),
    code: new FormControl(typeTemplate.code, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    libFr: new FormControl(typeTemplate.libFr, { validators: [Validators.maxLength(50)] }),
    libAr: new FormControl(typeTemplate.libAr, { validators: [Validators.maxLength(50)] }),
  });

  constructor(private typeService: TypeManagementService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ type }) => {
      if (type) {
        this.editForm.reset(type);
      } else {
        this.editForm.reset(newType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const type = this.editForm.getRawValue();
    if (type.id !== null) {
      this.typeService.update(type).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.typeService.create(type).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
