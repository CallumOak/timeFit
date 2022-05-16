import { TestBed } from '@angular/core/testing';

import { RoutinePlanService } from './routine-plan.service';

describe('RoutinePlanService', () => {
  let service: RoutinePlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoutinePlanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
