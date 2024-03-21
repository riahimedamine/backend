import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {IDemandeConge} from '../conge-management.model';
import {CongeManagementService} from '../service/conge-management.service';

const demandeTemplate = {} as IDemandeConge;

const newDemande: IDemandeConge = {
  vld: 0
} as IDemandeConge;

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './conge-management-update.component.html',
})
export class CongeManagementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = new FormGroup({
    id: new FormControl(demandeTemplate.id, {nonNullable: true}),

    dateDebut: new FormControl(demandeTemplate.dateDebut, {validators: [Validators.required], nonNullable: true}),
    dateFin: new FormControl(demandeTemplate.dateFin, {validators: [Validators.required], nonNullable: true}),
    type: new FormControl(demandeTemplate.type, {nonNullable: true}),
    notes: new FormControl(demandeTemplate.notes, {validators: [Validators.maxLength(100)]}),
    telephone: new FormControl(demandeTemplate.telephone, {validators: [Validators.minLength(8)]}),
    address: new FormControl(demandeTemplate.address, {validators: [Validators.maxLength(50)]}),
  });

  constructor(private congeService: CongeManagementService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({user: conge}) => {
      if (conge) {
        this.editForm.reset(conge);
      } else {
        this.editForm.reset(newDemande);
      }
    });
  }

  previousState(): void {
    window.history.back();
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
