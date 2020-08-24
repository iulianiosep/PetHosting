import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService} from '../../common/services/constants.service';
import { map } from 'rxjs/operators';
import {Team} from '../../shared/models/team';
@Injectable({
  providedIn: 'root'
})
export class LeagueManagerService {

  constructor(private httpClient: HttpClient,
                 private _constant: ConstantsService) { }
  public getTeams(){
         return this.httpClient.get<any>(this._constant.baseAppUrl + '/leagueManager/getTeams');
     }
  public updateTeam(id, name, country, balance){
      return this.httpClient.put<any>(this._constant.baseAppUrl + '/leagueManager/modifyTeam',{id, name, country, balance});
  }
  public deleteTeam(teamId){
      return this.httpClient.delete<any>(this._constant.baseAppUrl + '/leagueManager/deleteTeam/'+teamId);
  }

}
