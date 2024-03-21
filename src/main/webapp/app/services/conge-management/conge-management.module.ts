import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {CongeManagementComponent} from './list/conge-management.component';
import {CongeManagementDeleteDialogComponent} from './delete/conge-management-delete-dialog.component';
import {congeManagementRoute} from './conge-management.route';
import {CongeManagementUpdateComponent} from "./update/conge-management-update.component";

@NgModule({
  imports: [SharedModule, RouterModule.forChild(congeManagementRoute)],
  declarations: [
    CongeManagementComponent,
    CongeManagementDeleteDialogComponent,
    CongeManagementUpdateComponent
  ],
})
export class CongeManagementModule {}
