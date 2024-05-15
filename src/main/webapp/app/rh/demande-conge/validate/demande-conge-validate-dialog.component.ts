import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DemandeConge} from '../demande-conge.model';
import {DemandeCongeService} from '../service/demande-conge.service';

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

  validate(taskId: string): void {
    this.demandeService.validate(taskId).subscribe(() => {
      this.activeModal.close('validated');
    });
  }

  refuse(taskId: string): void {
    this.demandeService.refuse(taskId).subscribe(() => {
      this.activeModal.close('refused');
    });
  }
}
