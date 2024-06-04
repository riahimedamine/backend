import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { TypeCongeComponent } from './list/type-conge.component';

import { TypeCongeUpdateComponent } from './update/type-conge-update.component';
import { TypeCongeDeleteDialogComponent } from './delete/type-conge-delete-dialog.component';
import { typeCongeManagementRoute } from './type-conge-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(typeCongeManagementRoute)],
  declarations: [TypeCongeComponent, TypeCongeUpdateComponent, TypeCongeDeleteDialogComponent]
})
export class TypeCongeManagementModule {}
