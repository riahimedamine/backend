import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { IYear } from './year-management.model';
import { YearManagementService } from './service/year-management.service';
import { YearManagementComponent } from './list/year-management.component';
import { YearManagementUpdateComponent } from './update/year-management-update.component';

@Injectable({ providedIn: 'root' })
export class YearManagementResolve implements Resolve<IYear | null> {
  constructor(private service: YearManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IYear | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const yearManagementRoute: Routes = [
  {
    path: '',
    component: YearManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: 'new',
    component: YearManagementUpdateComponent,
    resolve: {
      year: YearManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: YearManagementUpdateComponent,
    resolve: {
      year: YearManagementResolve,
    },
  },
];
