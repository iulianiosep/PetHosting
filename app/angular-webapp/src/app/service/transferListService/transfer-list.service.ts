import { Injectable, EventEmitter, Output } from '@angular/core';
import { ConstantsService} from '../../common/services/constants.service';
import { map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class TransferListService {
  @Output() getUserBought: EventEmitter<any> = new EventEmitter();

  constructor(private httpClient: HttpClient,
                 private _constant: ConstantsService) { }

  public getPlayersFromTransferList(firstName, lastName,country, teamName, minValue, maxValue){
      debugger;
      return this.httpClient.get<any>(this._constant.baseAppUrl + '/transfers/search/firstName='+firstName+'/lastName='+lastName+'/country='+country+'/teamName='+teamName+'/minValue='+minValue+'/maxValue='+maxValue);
  }
  public buyPlayer(playerId){
        debugger;
        let result = this.httpClient.post<any>(this._constant.baseAppUrl + '/transfers/buy/'+playerId,{});
        this.getUserBought.emit("user bought");
        return result;
    }

}
