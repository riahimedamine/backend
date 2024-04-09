import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ISolde } from '../solde-management.model';
import { SlodeManagementService } from '../service/solde-management.service';
import { YearService } from '../../../core/util/year.service';
import { UserService } from '../../../core/util/user.service';
import { UserItem } from '../../../core/util/userItem';

const soldeTemplate = {} as ISolde;

const newSolde: ISolde = {} as ISolde;

@Component({
  selector: 'jhi-solde-mgmt-update',
  templateUrl: './solde-management-update.component.html',
})
export class SoldeManagementUpdateComponent implements OnInit {
  users: UserItem[] = [];
  years: number[] = [];
  isSaving = false;

  editForm = new FormGroup({
    id: new FormControl(soldeTemplate.id),
    solde: new FormControl(soldeTemplate.solde, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    year: new FormControl(soldeTemplate.year, { nonNullable: true, validators: [Validators.required] }),
    user: new FormControl(soldeTemplate.user, { nonNullable: true, validators: [Validators.maxLength(50), Validators.required] }),
  });

  constructor(
    private soldeService: SlodeManagementService,
    private route: ActivatedRoute,
    private yearsService: YearService,
    private userServise: UserService
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ solde }) => {
      if (solde) {
        this.editForm.reset(solde);
      } else {
        this.editForm.reset(newSolde);
      }
    });
    this.yearsService.getYears().subscribe({
      next: years => {
        this.years = years;
      },
    });
    this.userServise.getUsers().subscribe({
      next: users => {
        this.users = users;
      },
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const solde = this.editForm.getRawValue();
    if (solde.id !== null) {
      this.soldeService.update(solde).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.soldeService.create(solde).subscribe({
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
