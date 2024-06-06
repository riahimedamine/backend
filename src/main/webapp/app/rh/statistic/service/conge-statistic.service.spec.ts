import { TestBed } from '@angular/core/testing';

import { CongeStatisticService } from './conge-statistic.service';

describe('CongeStatisticService', () => {
  let service: CongeStatisticService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CongeStatisticService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
