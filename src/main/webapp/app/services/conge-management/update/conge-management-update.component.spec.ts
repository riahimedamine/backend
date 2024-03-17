import { ComponentFixture, TestBed, waitForAsync, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/config/authority.constants';
import { CongeManagementService } from '../service/conge-management.service';
import { User } from '../conge-management.model';

import { CongeManagementUpdateComponent } from './conge-management-update.component';

describe('User Management Update Component', () => {
  let comp: CongeManagementUpdateComponent;
  let fixture: ComponentFixture<CongeManagementUpdateComponent>;
  let service: CongeManagementService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CongeManagementUpdateComponent],
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
      .overrideTemplate(CongeManagementUpdateComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CongeManagementUpdateComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CongeManagementService);
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
