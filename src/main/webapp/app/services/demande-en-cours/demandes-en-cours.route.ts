import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Routes} from '@angular/router';
import {Observable, of} from 'rxjs';

import {IDemandeConge} from './demande-conge.model';
import {DemandesEnCoursService} from './service/demandes-en-cours.service';
import {DemandesEnCoursComponent} from './list/demandes-en-cours.component';
import {DemandesEnCoursDetailComponent} from "./detail/demandes-en-cours-detail.component";

@Injectable({providedIn: 'root'})
export class DemandeCongeResolve implements Resolve<IDemandeConge | null> {
  constructor(private service: DemandesEnCoursService) {
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
    component: DemandesEnCoursComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },

  {
    path: ':id/view',
    component: DemandesEnCoursDetailComponent,
    resolve: {
      demande: DemandeCongeResolve,
    },
  },
];
