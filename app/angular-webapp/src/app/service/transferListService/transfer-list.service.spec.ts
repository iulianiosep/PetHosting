import { TestBed } from '@angular/core/testing';

import { TransferListService } from './transfer-list.service';

describe('TransferListService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TransferListService = TestBed.get(TransferListService);
    expect(service).toBeTruthy();
  });
});
