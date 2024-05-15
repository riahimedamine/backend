import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {IDemandeConge} from '../demande-conge.model';

@Injectable({providedIn: 'root'})
export class DemandesEnCoursService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/tasks');
  private demandeUrl = this.applicationConfigService.getEndpointFor('api/demande-conges');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  find(id: number): Observable<IDemandeConge> {
    return this.http.get<IDemandeConge>(`${this.demandeUrl}/${id}`);
  }

  query(): Observable<HttpResponse<IDemandeConge[]>> {
    return this.http.get<IDemandeConge[]>(`${this.demandeUrl}/validate`, {observe: 'response'});
  }

  validate(id: string): Observable<HttpResponse<IDemandeConge>> {
    return this.http.patch<IDemandeConge>(`${this.demandeUrl}/validate/${id}?vld=2`, null, {observe: 'response'});
  }

  refuse(id: string): Observable<HttpResponse<IDemandeConge>> {
    return this.http.patch<IDemandeConge>(`${this.demandeUrl}/validate/${id}?vld=-1`, null, {observe: 'response'});
  }

  getSupervisees(): Observable<HttpResponse<string[]>> {
    return this.http.get<string[]>(`${this.resourceUrl}/supervisees`, {observe: 'response'});
  }
}
