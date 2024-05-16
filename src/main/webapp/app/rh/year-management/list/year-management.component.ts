import {Component, OnInit} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {combineLatest} from 'rxjs';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

import {ITEMS_PER_PAGE} from 'app/config/pagination.constants';
import {ASC, DESC, SORT} from 'app/config/navigation.constants';
import {AccountService} from 'app/core/auth/account.service';
import {YearManagementService} from '../service/year-management.service';
import {Year} from '../year-management.model';
import {YearManagementDeleteDialogComponent} from '../delete/year-management-delete-dialog.component';

@Component({
  selector: 'jhi-year-mgmt',
  templateUrl: './year-management.component.html',
})
export class YearManagementComponent implements OnInit {
  years: Year[] | null = [];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    private yearService: YearManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackIdentity(_index: number, item: Year): number {
    return item.id!;
  }

  deleteYear(year: Year): void {
    const modalRef = this.modalService.open(YearManagementDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
    modalRef.componentInstance.year = year;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  loadAll(): void {
    this.isLoading = true;
    this.yearService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<Year[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
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

  private onSuccess(years: Year[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.years = years;
  }
}
