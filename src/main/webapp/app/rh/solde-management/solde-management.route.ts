import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Observable, of} from 'rxjs';

import {ISolde} from './solde-management.model';
import {SlodeManagementService} from './service/solde-management.service';
import {SoldeManagementComponent} from './list/solde-management.component';
import {SoldeManagementUpdateComponent} from './update/solde-management-update.component';

@Injectable({ providedIn: 'root' })
export class SoldeManagementResolve implements Resolve<ISolde | null> {
  constructor(private service: SlodeManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISolde | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const soldeManagementRoute: Routes = [
  {
    path: '',
    component: SoldeManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: 'new',
    component: SoldeManagementUpdateComponent,
    resolve: {
      solde: SoldeManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: SoldeManagementUpdateComponent,
    resolve: {
      solde: SoldeManagementResolve,
    },
  },
];
