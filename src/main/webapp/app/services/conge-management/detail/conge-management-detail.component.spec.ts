import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';
import { User } from '../conge-management.model';

import { CongeManagementDetailComponent } from './conge-management-detail.component';

describe('User Management Detail Component', () => {
  let comp: CongeManagementDetailComponent;
  let fixture: ComponentFixture<CongeManagementDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CongeManagementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ user: new User(123, 'user', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') }),
          },
        },
      ],
    })
      .overrideTemplate(CongeManagementDetailComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CongeManagementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.user).toEqual(
        expect.objectContaining({
          id: 123,
          login: 'user',
          firstName: 'first',
          lastName: 'last',
          email: 'first@last.com',
          activated: true,
          langKey: 'en',
          authorities: [Authority.USER],
          createdBy: 'admin',
        })
      );
    });
  });
});
