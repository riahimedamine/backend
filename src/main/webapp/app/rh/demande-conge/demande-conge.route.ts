import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Observable, of} from 'rxjs';

import {IDemandeConge} from './demande-conge.model';
import {DemandeCongeService} from './service/demande-conge.service';
import {DemandeCongeComponent} from './list/demande-conge.component';
import {DemandeCongeDetailComponent} from "./detail/demande-conge-detail.component";

@Injectable({ providedIn: 'root' })
export class DemandeCongeResolve implements Resolve<IDemandeConge | null> {
  constructor(private service: DemandeCongeService) {}

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
    component: DemandeCongeComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: ':id/view',
    component: DemandeCongeDetailComponent,
    resolve: {
      demande: DemandeCongeResolve,
    },
  },
];
