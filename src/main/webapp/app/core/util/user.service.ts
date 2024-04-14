import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../config/application-config.service';
import { Observable } from 'rxjs';
import { UserItem } from './userItem';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/users');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getUsers(): Observable<UserItem[]> {
    return this.http.get<UserItem[]>(`${this.resourceUrl}`);
  }
}
