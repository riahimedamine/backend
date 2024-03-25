import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TypeCongeManagementComponent } from './list/type-conge-management.component';

import { TypeCongesManagementUpdateComponent } from './update/type-conges-management-update.component';
import { TypeCongesManagementDeleteDialogComponent } from './delete/type-conges-management-delete-dialog.component';
import { typeCongeManagementRoute } from './type-conge-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(typeCongeManagementRoute)],
  declarations: [TypeCongeManagementComponent, TypeCongesManagementUpdateComponent, TypeCongesManagementDeleteDialogComponent],
})
export class TypeCongeManagementModule {}
