jest.mock('app/core/auth/account.service');

import { ComponentFixture, fakeAsync, inject, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { UserManagementService } from '../service/year-management.service';
import { User } from '../year-management.model';
import { AccountService } from 'app/core/auth/account.service';

import { YearManagementComponent } from './year-management.component';

describe('User Management Component', () => {
  let comp: YearManagementComponent;
  let fixture: ComponentFixture<YearManagementComponent>;
  let service: UserManagementService;
  let mockAccountService: AccountService;
  const data = of({
    defaultSort: 'id,asc',
  });
  const queryParamMap = of(
    jest.requireActual('@angular/router').convertToParamMap({
      page: '1',
      size: '1',
      sort: 'id,desc',
    })
  );

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [YearManagementComponent],
      providers: [{ provide: ActivatedRoute, useValue: { data, queryParamMap } }, AccountService],
    })
      .overrideTemplate(YearManagementComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearManagementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserManagementService);
    mockAccountService = TestBed.inject(AccountService);
    mockAccountService.identity = jest.fn(() => of(null));
  });

  describe('OnInit', () => {
    it('Should call load all on init', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [new User(123)],
              headers,
            })
          )
        );

        // WHEN
        comp.ngOnInit();
        tick(); // simulate async

        // THEN
        expect(service.query).toHaveBeenCalled();
        expect(comp.users?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      })
    ));
  });

  describe('setActive', () => {
    it('Should update user and call load all', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const headers = new HttpHeaders().append('link', 'link;link');
        const user = new User(123);
        jest.spyOn(service, 'query').mockReturnValue(
          of(
            new HttpResponse({
              body: [user],
              headers,
            })
          )
        );
        jest.spyOn(service, 'update').mockReturnValue(of(user));

        // WHEN
        comp.setActive(user, true);
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith({ ...user, activated: true });
        expect(service.query).toHaveBeenCalled();
        expect(comp.users?.[0]).toEqual(expect.objectContaining({ id: 123 }));
      })
    ));
  });
});
