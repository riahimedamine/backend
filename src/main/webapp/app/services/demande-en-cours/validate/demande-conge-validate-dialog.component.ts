import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DemandesEnCoursService} from "../service/demandes-en-cours.service";
import {IDemandeConge} from "../demande-conge.model";

@Component({
  selector: 'jhi-type-conge-mgmt-delete-dialog',
  templateUrl: './demande-conge-validate-dialog.component.html',
})
export class DemandeCongeValidateDialogComponent {
  demande?: IDemandeConge;

  constructor(private demandeService: DemandesEnCoursService, private activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  validate(id: string): void {
    this.demandeService.validate(id).subscribe(() => {
      this.activeModal.close('validated');
    });
  }

  refuse(id: string): void {
    this.demandeService.refuse(id).subscribe(() => {
      this.activeModal.close('refused');
    });
  }
}
