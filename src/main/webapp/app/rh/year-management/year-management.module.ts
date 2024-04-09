import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { YearManagementComponent } from './list/year-management.component';

import { YearManagementUpdateComponent } from './update/year-management-update.component';
import { YearManagementDeleteDialogComponent } from './delete/year-management-delete-dialog.component';
import { yearManagementRoute } from './year-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(yearManagementRoute)],
  declarations: [YearManagementComponent, YearManagementUpdateComponent, YearManagementDeleteDialogComponent],
})
export class YearManagementModule {}
