import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { ITypeConge } from './type-conge-management.model';
import { TypeManagementService } from './service/type-conges-management.service';
import { TypeCongeManagementComponent } from './list/type-conge-management.component';
import { TypeCongesManagementUpdateComponent } from './update/type-conges-management-update.component';

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
    component: TypeCongeManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: 'new',
    component: TypeCongesManagementUpdateComponent,
    resolve: {
      type: TypeManagementResolve,
    },
  },
  {
    path: ':login/edit',
    component: TypeCongesManagementUpdateComponent,
    resolve: {
      type: TypeManagementResolve,
    },
  },
];
