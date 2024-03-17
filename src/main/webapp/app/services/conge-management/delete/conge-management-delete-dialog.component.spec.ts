jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, waitForAsync, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { CongeManagementService } from '../service/conge-management.service';

import { CongeManagementDeleteDialogComponent } from './conge-management-delete-dialog.component';

describe('User Management Delete Component', () => {
  let comp: CongeManagementDeleteDialogComponent;
  let fixture: ComponentFixture<CongeManagementDeleteDialogComponent>;
  let service: CongeManagementService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CongeManagementDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(CongeManagementDeleteDialogComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CongeManagementDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CongeManagementService);
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
