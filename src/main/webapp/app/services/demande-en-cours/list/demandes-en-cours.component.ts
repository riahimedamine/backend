import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ASC, DESC, SORT } from 'app/config/navigation.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { DemandesEnCoursService } from '../service/demandes-en-cours.service';
import { DemandeCongeValidateDialogComponent } from '../validate/demande-conge-validate-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IDemandeConge } from '../demande-conge.model';

@Component({
  selector: 'jhi-conge-mgmt',
  templateUrl: './demandes-en-cours.component.html',
})
export class DemandesEnCoursComponent implements OnInit {
  currentAccount: Account | null = null;
  demandes: IDemandeConge[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  users: string[] | null = [];

  constructor(
    private demandesEnCoursService: DemandesEnCoursService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.demandesEnCoursService.getSupervisees().subscribe(res => {
      this.users = res.body;
    });
    this.handleNavigation();
  }

  trackIdentity(_index: number, item: IDemandeConge): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.demandesEnCoursService.query()
      .subscribe(
        {
          next: (res: HttpResponse<IDemandeConge[]>) => this.onSuccess(res.body, res.headers),
          error: () => (this.isLoading = false),
        }
      );
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  validate(demande: IDemandeConge): void {
    const modalRef = this.modalService.open(DemandeCongeValidateDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.demande = demande;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'validated' || reason === 'refused') {
        this.loadAll();
      }
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(tasks: IDemandeConge[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.demandes = tasks;
    this.isLoading = false;
  }
}
