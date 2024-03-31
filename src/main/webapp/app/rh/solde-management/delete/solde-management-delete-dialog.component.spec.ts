jest.mock('@ng-bootstrap/ng-bootstrap');

import {ComponentFixture, fakeAsync, inject, TestBed, tick, waitForAsync} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {of} from 'rxjs';

import {UserManagementService} from '../service/solde-management.service';

import {SoldeManagementDeleteDialogComponent} from './solde-management-delete-dialog.component';

describe('User Management Delete Component', () => {
  let comp: SoldeManagementDeleteDialogComponent;
  let fixture: ComponentFixture<SoldeManagementDeleteDialogComponent>;
  let service: UserManagementService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SoldeManagementDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SoldeManagementDeleteDialogComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SoldeManagementDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserManagementService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of({}));

        // WHEN
        comp.confirmDelete('user');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('user');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));
  });
});
