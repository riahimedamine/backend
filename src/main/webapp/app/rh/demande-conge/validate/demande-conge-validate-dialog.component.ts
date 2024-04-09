import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DemandeCongeComponent } from '../list/demande-conge.component';
import { DemandeConge } from '../demande-conge.model';
import { DemandeCongeService } from '../service/demande-conge.service';

@Component({
  selector: 'jhi-type-conge-mgmt-delete-dialog',
  templateUrl: './demande-conge-validate-dialog.component.html',
})
export class DemandeCongeValidateDialogComponent {
  demande?: DemandeConge;

  constructor(private demandeService: DemandeCongeService, private activeModal: NgbActiveModal) {}

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
