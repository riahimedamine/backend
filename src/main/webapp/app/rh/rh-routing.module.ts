import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'demande-conge',
        loadChildren: () => import('./demande-conge/demande-conge.module').then(m => m.DemandeCongeModule),
        data: {
          pageTitle: 'congeManagement.home.title',
        },
      },
      {
        path: 'solde-management',
        loadChildren: () => import('./solde-management/solde-management.module').then(m => m.SoldeManagementModule),
        data: {
          pageTitle: 'soldeManagement.home.title',
        },
      },
      {
        path: 'year-management',
        loadChildren: () => import('./year-management/year-management.module').then(m => m.YearManagementModule),
        data: {
          pageTitle: 'yearManagement.home.title',
        },
      },

      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class RhRoutingModule {}
