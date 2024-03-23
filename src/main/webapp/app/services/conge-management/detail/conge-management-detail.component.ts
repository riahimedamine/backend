import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DemandeConge } from '../conge-management.model';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './conge-management-detail.component.html',
})
export class CongeManagementDetailComponent implements OnInit {
  conge: DemandeConge | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ conge }) => {
      this.conge = conge;
    });
  }
}
