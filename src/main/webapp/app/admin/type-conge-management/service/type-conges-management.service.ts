import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

import {ApplicationConfigService} from 'app/core/config/application-config.service';
import {createRequestOption} from 'app/core/request/request-util';
import {Pagination} from 'app/core/request/request.model';
import {ITypeConge} from '../type-conge-management.model';

@Injectable({ providedIn: 'root' })
export class TypeManagementService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/type-conges');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(type: ITypeConge): Observable<ITypeConge> {
    return this.http.post<ITypeConge>(this.resourceUrl, type);
  }

  update(type: ITypeConge): Observable<ITypeConge> {
    return this.http.put<ITypeConge>(this.resourceUrl, type);
  }

  find(id: number): Observable<ITypeConge> {
    return this.http.get<ITypeConge>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<ITypeConge[]>> {
    const options = createRequestOption(req);
    return this.http.get<ITypeConge[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }
}
