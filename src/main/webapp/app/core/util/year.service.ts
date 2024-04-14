import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../config/application-config.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class YearService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/years');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getYears(): Observable<number[]> {
    return this.http.get<number[]>(`${this.resourceUrl}/all`);
  }
}
