import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Year } from '../year-management.model';
import { YearManagementService } from '../service/year-management.service';

@Component({
  selector: 'jhi-type-year-mgmt-delete-dialog',
  templateUrl: './year-management-delete-dialog.component.html',
})
export class YearManagementDeleteDialogComponent {
  year?: Year;

  constructor(private yearService: YearManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.yearService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
