import { TestBed } from '@angular/core/testing';

import { LeagueManagerService } from './league-manager.service';

describe('LeagueManagerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LeagueManagerService = TestBed.get(LeagueManagerService);
    expect(service).toBeTruthy();
  });
});
