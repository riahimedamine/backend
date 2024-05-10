import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {DemandeConge} from '../demande-conge.model';
import {DemandesEnCoursService} from "../service/demandes-en-cours.service";

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './demandes-en-cours-detail.component.html',
})
export class DemandesEnCoursDetailComponent implements OnInit {
  demande: DemandeConge | null = null;

  constructor(private congeService: DemandesEnCoursService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({demande}) => {
      this.demande = demande;
    });
  }
}
