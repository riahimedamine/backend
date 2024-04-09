import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/core/request/request.model';
import { IYear } from '../year-management.model';

@Injectable({ providedIn: 'root' })
export class YearManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/years');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(year: IYear): Observable<IYear> {
    console.error('year', year);
    return this.http.post<IYear>(this.resourceUrl, year);
  }

  update(year: IYear): Observable<IYear> {
    console.log('year', year);
    return this.http.put<IYear>(this.resourceUrl, year);
  }

  find(id: number): Observable<IYear> {
    return this.http.get<IYear>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IYear[]>> {
    const options = createRequestOption(req);
    return this.http.get<IYear[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }
}
