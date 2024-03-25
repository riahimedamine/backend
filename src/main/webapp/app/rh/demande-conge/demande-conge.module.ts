import { NgModule } from '@angular/core';

import { DemandeCongeComponent } from './list/demande-conge.component';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule } from '@angular/router';
import { congeManagementRoute } from './demande-conge.route';

@NgModule({
  declarations: [DemandeCongeComponent],
  imports: [SharedModule, RouterModule.forChild(congeManagementRoute)],
})
export class DemandeCongeModule {}
