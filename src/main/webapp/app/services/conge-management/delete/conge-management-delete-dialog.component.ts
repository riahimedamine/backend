import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { User } from '../conge-management.model';
import { CongeManagementService } from '../service/conge-management.service';

@Component({
  selector: 'jhi-user-mgmt-delete-dialog',
  templateUrl: './conge-management-delete-dialog.component.html',
})
export class CongeManagementDeleteDialogComponent {
  user?: User;

  constructor(private userService: CongeManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(login: string): void {
    this.userService.delete(login).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
