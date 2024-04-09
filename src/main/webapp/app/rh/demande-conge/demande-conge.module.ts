import { NgModule } from '@angular/core';

import { DemandeCongeComponent } from './list/demande-conge.component';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule } from '@angular/router';
import { congeManagementRoute } from './demande-conge.route';
import { DemandeCongeDetailComponent } from './detail/demande-conge-detail.component';
import { DemandeCongeValidateDialogComponent } from './validate/demande-conge-validate-dialog.component';

@NgModule({
  declarations: [DemandeCongeComponent, DemandeCongeDetailComponent, DemandeCongeValidateDialogComponent],
  imports: [SharedModule, RouterModule.forChild(congeManagementRoute)],
})
export class DemandeCongeModule {}
