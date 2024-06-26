import {Component, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {combineLatest} from 'rxjs';

import {ITEMS_PER_PAGE} from 'app/config/pagination.constants';
import {ASC, DESC, SORT} from 'app/config/navigation.constants';
import {AccountService} from 'app/core/auth/account.service';
import {Account} from 'app/core/auth/account.model';
import {DemandeCongeService} from '../service/demande-conge.service';
import {DemandeConge} from '../demande-conge.model';
import {DemandeCongeValidateDialogComponent} from '../validate/demande-conge-validate-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-conge-mgmt',
  templateUrl: './demande-conge.component.html',
})
export class DemandeCongeComponent implements OnInit {
  currentAccount: Account | null = null;
  demandes: DemandeConge[] | null = null;
  activeDemandes: DemandeConge[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    private congeService: DemandeCongeService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  trackIdentity(_index: number, item: DemandeConge): number {
    return item.id!;
  }

  loadAll(): void {
    this.isLoading = true;
    this.congeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<DemandeConge[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        error: () => (this.isLoading = false),
      });

    this.congeService.getActive().subscribe({
      next: (res: HttpResponse<DemandeConge[]>) => {
        this.isLoading = false;
        this.activeDemandes = res.body;
      },
      error: () => (this.isLoading = false),
    });
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

  validate(demande: DemandeConge): void {
    const modalRef = this.modalService.open(DemandeCongeValidateDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.demande = demande;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'validated') {
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

  private onSuccess(demandes: DemandeConge[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.demandes = demandes;
  }
}
