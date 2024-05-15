import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {IDemandeConge} from '../conge-management.model';
import {CongeManagementService} from '../service/conge-management.service';
import {LANGUAGES} from '../../../config/language.constants';
import {Account} from '../../../core/auth/account.model';
import {AccountService} from '../../../core/auth/account.service';

function DateValidator(dateDebutControl: AbstractControl): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control.value && dateDebutControl.value) {
      const dateDebut = new Date(dateDebutControl.value);
      const dateFin = new Date(control.value);
      return dateFin > dateDebut ? null : {dateFinAfterDateDebut: true};
    }
    return null;
  };
}

function DateDebutValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control.value) {
      const dateDebut = new Date(control.value);
      return dateDebut > new Date() ? null : {dateDebutBeforeToday: true};
    }
    return null;
  };
}

const demandeTemplate = {} as IDemandeConge;

const newDemande: IDemandeConge = {
  vld: 0,
} as IDemandeConge;

@Component({
  selector: 'jhi-conge-mgmt-update',
  templateUrl: './conge-management-update.component.html',
})
export class CongeManagementUpdateComponent implements OnInit {
  isSaving = false;
  account: Account = {} as Account;
  types: any[] = [];
  error: {
    hasOne: boolean;
    solde: boolean;
  } | null = null;

  editForm = new FormGroup({
    id: new FormControl(demandeTemplate.id, {nonNullable: true}),

    dateDebut: new FormControl(demandeTemplate.dateDebut, {validators: [Validators.required], nonNullable: true}),
    dateFin: new FormControl(demandeTemplate.dateFin, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    type: new FormControl(demandeTemplate.type, {validators: [Validators.required], nonNullable: true}),
    notes: new FormControl(demandeTemplate.notes, {validators: [Validators.maxLength(100)]}),
    telephone: new FormControl(demandeTemplate.telephone, {validators: [Validators.minLength(8)]}),
    address: new FormControl(demandeTemplate.address, {validators: [Validators.maxLength(50)]}),
  });
  protected readonly languages = LANGUAGES;

  constructor(private congeService: CongeManagementService, private route: ActivatedRoute, private accountService: AccountService) {
    this.editForm.get('dateDebut')?.setValidators([Validators.required, DateDebutValidator()]);
    this.editForm.get('dateFin')?.setValidators([Validators.required, DateValidator(this.editForm.get('dateDebut')!)]);
  }

  previousState(): void {
    window.history.back();
  }

  ngOnInit(): void {
    this.route.data.subscribe(({conge}) => {
      if (conge) {
        this.editForm.reset(conge);
      } else {
        this.editForm.reset(newDemande);
      }
    });

    this.congeService.getTypes().subscribe(response => {
      this.types = response;
    });

    this.accountService.identity().subscribe(account => {
      if (account != null) {
        this.account = account;
      }
    });
  }

  check(): void {
    if (this.editForm.get('dateDebut')!.value && this.editForm.get('dateFin')!.value) {
      const obj = {
        dateDebut: this.editForm.get('dateDebut')!.value!,
        dateFin: this.editForm.get('dateFin')!.value!,
      };

      this.congeService.check(obj).subscribe(response => {
        this.error = response;
      });
    }
  }

  save(): void {
    this.isSaving = true;
    const demande = this.editForm.getRawValue();
    if (demande.id !== null) {
      this.congeService.update(demande).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    } else {
      this.congeService.create(demande).subscribe({
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
