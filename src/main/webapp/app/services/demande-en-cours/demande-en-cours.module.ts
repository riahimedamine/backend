import {NgModule} from '@angular/core';

import {DemandesEnCoursComponent} from './list/demandes-en-cours.component';
import {SharedModule} from '../../shared/shared.module';
import {RouterModule} from '@angular/router';
import {congeManagementRoute} from './demandes-en-cours.route';
import {DemandesEnCoursDetailComponent} from './detail/demandes-en-cours-detail.component';
import {DemandeCongeValidateDialogComponent} from './validate/demande-conge-validate-dialog.component';

@NgModule({
  declarations: [DemandesEnCoursComponent, DemandesEnCoursDetailComponent, DemandeCongeValidateDialogComponent],
  imports: [SharedModule, RouterModule.forChild(congeManagementRoute)],
})
export class DemandeEnCoursModule {
}
