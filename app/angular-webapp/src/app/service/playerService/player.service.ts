import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService} from '../../common/services/constants.service';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  constructor(private httpClient: HttpClient,
                 private _constant: ConstantsService) { }

  public submitPlayerToTransferList(playerId, playerValue){
        return this.httpClient.post<any>(this._constant.baseAppUrl + '/player/addToTransferList',{playerId, playerValue});
    }
  public removePlayerFromTransferList(playerId){
        return this.httpClient.delete<any>(this._constant.baseAppUrl + '/player/removeFromTransferList/'+playerId);
  }

}
