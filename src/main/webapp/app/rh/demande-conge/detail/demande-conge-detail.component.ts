import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {DemandeConge} from '../demande-conge.model';
import {DemandeCongeService} from "../service/demande-conge.service";

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './demande-conge-detail.component.html',
})
export class DemandeCongeDetailComponent implements OnInit {
  demande: DemandeConge | null = null;

  constructor(private congeService: DemandeCongeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(({demande}) => {
      this.demande = demande;
    });
  }
}
