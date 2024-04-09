import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IYear } from '../year-management.model';
import { YearManagementService } from '../service/year-management.service';

const yearTemplate = {} as IYear;

const newYear: IYear = {} as IYear;

@Component({
  selector: 'jhi-year-mgmt-update',
  templateUrl: './year-management-update.component.html',
})
export class YearManagementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = new FormGroup({
    id: new FormControl(yearTemplate.id),
    year: new FormControl(yearTemplate.year, {
      nonNullable: true,
      validators: [Validators.required],
    }),
  });

  constructor(private yearService: YearManagementService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ year }) => {
      if (year) {
        this.editForm.reset(year);
      } else {
        this.editForm.reset(newYear);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const year = this.editForm.getRawValue();
    if (year.id !== null) {
      this.yearService.update(year).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.yearService.create(year).subscribe({
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
