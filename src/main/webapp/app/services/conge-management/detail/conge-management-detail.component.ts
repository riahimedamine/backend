import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from '../conge-management.model';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './conge-management-detail.component.html',
})
export class CongeManagementDetailComponent implements OnInit {
  user: User | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      this.user = user;
    });
  }
}
