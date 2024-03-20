import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Observable, of} from 'rxjs';

import {IDemandeConge} from './conge-management.model';
import {CongeManagementService} from './service/conge-management.service';
import {CongeManagementComponent} from './list/conge-management.component';

@Injectable({providedIn: 'root'})
export class CongeManagementResolve implements Resolve<IDemandeConge | null> {
  constructor(private service: CongeManagementService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<IDemandeConge | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const congeManagementRoute: Routes = [
  {
    path: '',
    component: CongeManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
];
