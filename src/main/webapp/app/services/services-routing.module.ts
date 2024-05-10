import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'conge-management',
        loadChildren: () => import('./conge-management/conge-management.module').then(m => m.CongeManagementModule),
        data: {
          pageTitle: 'congeManagement.home.title',
        },
      },
      {
        path: 'demandes-en-cours',
        loadChildren: () => import('./demande-en-cours/demande-en-cours.module').then(m => m.DemandeEnCoursModule),
        data: {
          pageTitle: 'demandeEnCours.home.title',
        },
      }
    ]),
  ],
})
export class ServicesRoutingModule {
}
