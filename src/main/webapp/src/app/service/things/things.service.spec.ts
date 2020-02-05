import { TestBed } from '@angular/core/testing';

import { ThingsService } from './things.service';

describe('ThingsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ThingsService = TestBed.get(ThingsService);
    expect(service).toBeTruthy();
  });
});
