import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DemandeConge} from '../demande-conge.model';
import {DemandesEnCoursService} from "../service/demandes-en-cours.service";

@Component({
  selector: 'jhi-type-conge-mgmt-delete-dialog',
  templateUrl: './demande-conge-validate-dialog.component.html',
})
export class DemandeCongeValidateDialogComponent {
  demande?: DemandeConge;

  constructor(private demandeService: DemandesEnCoursService, private activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  validate(id: number): void {
    this.demandeService.validate(id).subscribe(() => {
      this.activeModal.close('validated');
    });
  }

  refuse(id: number): void {
    this.demandeService.refuse(id).subscribe(() => {
      this.activeModal.close('refused');
    });
  }
}
