import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { ICongeStatistic } from '../conge-statistic.model';

@Injectable({
  providedIn: 'root'
})
export class CongeStatisticService {

  private resourceUrl = this.applicationConfigurtionService.getEndpointFor('api/statistics/conges');

  constructor(private http: HttpClient, private applicationConfigurtionService: ApplicationConfigService) {
  }

  loadAll(): Observable<ICongeStatistic[]> {
    return this.http.get<ICongeStatistic[]>(this.resourceUrl);
  }

  loadByYear(year: number): Observable<ICongeStatistic[]> {
    return this.http.get<ICongeStatistic[]>(`${this.resourceUrl}/${year}`);
  }

  loadByYearAndMonth(year: number, month: number): Observable<ICongeStatistic[]> {
    return this.http.get<ICongeStatistic[]>(`${this.resourceUrl}/${year}/${month}`);
  }

  refresh(): Observable<any> {
    return this.http.put<any>(`${this.resourceUrl}/refresh`, null);
  }
}
