import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { ISolde } from '../solde-management.model';

@Injectable({ providedIn: 'root' })
export class SlodeManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/solde-conges');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(solde: ISolde): Observable<ISolde> {
    console.log('solde', solde);
    return this.http.post<ISolde>(this.resourceUrl, solde);
  }

  update(solde: ISolde): Observable<ISolde> {
    console.log('solde', solde);
    return this.http.put<ISolde>(this.resourceUrl, solde);
  }

  find(id: number): Observable<ISolde> {
    return this.http.get<ISolde>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<ISolde[]>> {
    const options = createRequestOption(req);
    return this.http.get<ISolde[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }
}
