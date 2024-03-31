import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {SharedModule} from 'app/shared/shared.module';
import {SoldeManagementComponent} from './list/solde-management.component';

import {SoldeManagementUpdateComponent} from './update/solde-management-update.component';
import {SoldeManagementDeleteDialogComponent} from './delete/solde-management-delete-dialog.component';
import {soldeManagementRoute} from './solde-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(soldeManagementRoute)],
  declarations: [SoldeManagementComponent, SoldeManagementUpdateComponent, SoldeManagementDeleteDialogComponent],
})
export class SoldeManagementModule {}
