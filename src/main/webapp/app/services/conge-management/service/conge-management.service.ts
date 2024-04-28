import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IDemandeConge, ISoldeConge } from '../conge-management.model';
import { AccountService } from '../../../core/auth/account.service';

@Injectable({ providedIn: 'root' })
export class CongeManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/demande-conges');

  constructor(
    private accountService: AccountService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  create(demandeConge: IDemandeConge): Observable<IDemandeConge> {
    this.accountService.identity().subscribe(account => {
      if (account != null) demandeConge.user = account.login;
    });
    demandeConge.vld = 0;
    return this.http.post<IDemandeConge>(this.resourceUrl, demandeConge);
  }

  update(demandeConge: IDemandeConge): Observable<IDemandeConge> {
    this.accountService.identity().subscribe(account => {
      if (account != null) demandeConge.user = account.login;
    });
    demandeConge.vld = 0;
    return this.http.put<IDemandeConge>(this.resourceUrl, demandeConge);
  }

  find(id: number): Observable<IDemandeConge> {
    return this.http.get<IDemandeConge>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IDemandeConge[]>> {
    const options = createRequestOption(req);
    return this.http.get<IDemandeConge[]>(`${this.resourceUrl}/current-user`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  findByUser(login: string): Observable<IDemandeConge[]> {
    return this.http.get<IDemandeConge[]>(`${this.resourceUrl}/user/${login}`);
  }

  getTypes(): Observable<string[]> {
    return this.http.get<string[]>(`${this.resourceUrl}/types`);
  }

  getSolde(login: string): Observable<ISoldeConge[]> {
    return this.http.get<ISoldeConge[]>(`${this.resourceUrl}/solde/${login}`);
  }

  hasDemandeEnCours(login: string): boolean {
    let exist = false;
    this.findByUser(login).subscribe((demandes: IDemandeConge[]) => {
      for (const demande of demandes) {
        if (demande.vld === 0) {
          exist = true;
        }
      }
      exist = false;
    });
    return exist;
  }

  check(obj: any): Observable<boolean> {
    return this.http.post<boolean>(`${this.resourceUrl}/check`, obj);
  }
}
