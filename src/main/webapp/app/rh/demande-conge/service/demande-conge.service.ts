import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IDemandeConge } from '../demande-conge.model';
import { AccountService } from '../../../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class DemandeCongeService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-conges');

  constructor(
    private accountService: AccountService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  find(id: number): Observable<IDemandeConge> {
    return this.http.get<IDemandeConge>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IDemandeConge[]>> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeConge[]>(`${this.resourceUrl}`, { params: options, observe: 'response' });
  }

  validate(id: number): Observable<HttpResponse<IDemandeConge>> {
    return this.http.patch<IDemandeConge>(`${this.resourceUrl}/validate/${id}?vld=1`, null, { observe: 'response' });
  }
  refuse(id: number): Observable<HttpResponse<IDemandeConge>> {
    return this.http.patch<IDemandeConge>(`${this.resourceUrl}/validate/${id}?vld=2`, null, { observe: 'response' });
  }
}
