import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TypeConge } from '../type-conge-management.model';
import { TypeManagementService } from '../service/type-conges-management.service';

@Component({
  selector: 'jhi-type-conge-mgmt-delete-dialog',
  templateUrl: './type-conge-delete-dialog.component.html'
})
export class TypeCongeDeleteDialogComponent {
  typeConge?: TypeConge;

  constructor(private typeService: TypeManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
