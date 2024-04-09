import { ComponentFixture, fakeAsync, inject, TestBed, tick, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';
import { UserManagementService } from '../service/year-management.service';
import { User } from '../year-management.model';

import { YearManagementUpdateComponent } from './year-management-update.component';

describe('User Management Update Component', () => {
  let comp: YearManagementUpdateComponent;
  let fixture: ComponentFixture<YearManagementUpdateComponent>;
  let service: UserManagementService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [YearManagementUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ user: new User(123, 'user', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') }),
          },
        },
      ],
    })
      .overrideTemplate(YearManagementUpdateComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearManagementUpdateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserManagementService);
  });

  describe('OnInit', () => {
    it('Should load authorities and language on init', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'authorities').mockReturnValue(of(['USER']));

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.authorities).toHaveBeenCalled();
        expect(comp.authorities).toEqual(['USER']);
      })
    ));
  });

  describe('save', () => {
    it('Should call update service on save for existing user', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const entity = { id: 123 };
        jest.spyOn(service, 'update').mockReturnValue(of(entity));
        comp.editForm.patchValue(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(expect.objectContaining(entity));
        expect(comp.isSaving).toEqual(false);
      })
    ));

    it('Should call create service on save for new user', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        const entity = { login: 'foo' } as User;
        jest.spyOn(service, 'create').mockReturnValue(of(entity));
        comp.editForm.patchValue(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(comp.editForm.getRawValue().id).toBeNull();
        expect(service.create).toHaveBeenCalledWith(expect.objectContaining(entity));
        expect(comp.isSaving).toEqual(false);
      })
    ));
  });
});
