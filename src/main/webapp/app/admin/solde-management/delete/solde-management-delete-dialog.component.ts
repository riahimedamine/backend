import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Solde } from '../solde-management.model';
import { SlodeManagementService } from '../service/solde-management.service';

@Component({
  selector: 'jhi-type-conge-mgmt-delete-dialog',
  templateUrl: './solde-management-delete-dialog.component.html',
})
export class SoldeManagementDeleteDialogComponent {
  solde?: Solde;

  constructor(private soldeService: SlodeManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.soldeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
