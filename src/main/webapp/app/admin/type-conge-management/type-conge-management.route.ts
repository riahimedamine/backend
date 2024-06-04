import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ITypeConge } from './type-conge-management.model';
import { TypeManagementService } from './service/type-conges-management.service';
import { TypeCongeComponent } from './list/type-conge.component';
import { TypeCongeUpdateComponent } from './update/type-conge-update.component';

@Injectable({ providedIn: 'root' })
export class TypeManagementResolve implements Resolve<ITypeConge | null> {
  constructor(private service: TypeManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeConge | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const typeCongeManagementRoute: Routes = [
  {
    path: '',
    component: TypeCongeComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: 'new',
    component: TypeCongeUpdateComponent,
    resolve: {
      type: TypeManagementResolve,
    },
  },
  {
    path: ':id/edit',
    component: TypeCongeUpdateComponent,
    resolve: {
      type: TypeManagementResolve,
    },
  },
];
