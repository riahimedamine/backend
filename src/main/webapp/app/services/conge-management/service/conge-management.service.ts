import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IDemandeConge, ISoldeConge } from '../conge-management.model';
import { AccountService } from '../../../core/auth/account.service';

@Injectable({providedIn: 'root'})
export class CongeManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-conges');
  private resourceUrlSolde = this.applicationConfigService.getEndpointFor('api/solde-conges');

  constructor(
    private accountService: AccountService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {
  }

  create(demandeConge: IDemandeConge): Observable<IDemandeConge> {
    this.accountService.identity().subscribe(account => {
      if (account != null) {
        demandeConge.user = account.login;
      }
    });
    demandeConge.vld = 0;
    return this.http.post<IDemandeConge>(this.resourceUrl, demandeConge);
  }

  update(demandeConge: IDemandeConge): Observable<IDemandeConge> {
    this.accountService.identity().subscribe(account => {
      if (account != null) {
        demandeConge.user = account.login;
      }
    });
    demandeConge.vld = 0;
    return this.http.put<IDemandeConge>(this.resourceUrl, demandeConge);
  }

  find(id: number): Observable<IDemandeConge> {
    return this.http.get<IDemandeConge>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IDemandeConge[]>> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeConge[]>(`${this.resourceUrl}/current-user`, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.resourceUrl}/types`);
  }

  getSolde(): Observable<ISoldeConge[]> {
    return this.http.get<ISoldeConge[]>(`${this.resourceUrlSolde}/current-user`)
  }

  check(obj: { dateDebut: Date; dateFin: Date, isUpdate: boolean }): Observable<{ hasOne: boolean, solde: boolean }> {
    console.error('****************** obj', obj);
    return this.http.post<{ hasOne: boolean, solde: boolean }>(`${this.resourceUrl}/check`, obj);
  }
}
