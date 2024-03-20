import {Component} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {DemandeConge} from '../conge-management.model';
import {CongeManagementService} from '../service/conge-management.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './conge-management-delete-dialog.component.html',
})
export class CongeManagementDeleteDialogComponent {
  demande?: DemandeConge;

  constructor(private congeManagementService: CongeManagementService, private activeModal: NgbActiveModal) {
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.congeManagementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
