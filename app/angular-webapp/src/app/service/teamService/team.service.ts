import { Injectable, EventEmitter, Output  } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService} from '../../common/services/constants.service';
import { map } from 'rxjs/operators';
import {Team} from '../../shared/models/team';
@Injectable({
  providedIn: 'root'
})
export class TeamService {
  @Output() getUserId: EventEmitter<any> = new EventEmitter();
  @Output() updateHeader: EventEmitter<any> = new EventEmitter()
  constructor(private httpClient: HttpClient,
   private _constant: ConstantsService) { }

  public getTeam(){
       return this.httpClient.get<any>(this._constant.baseAppUrl + '/team').pipe(
          map(data=>{

                        this.getUserId.emit(data.user.id);
                        return Object.assign(new Team(), data);}
          )
       )
   }
  public updateHeaderComponent(){
    this.updateHeader.emit("Update header");
  }

  public createTeam(country, name){
      return this.httpClient.post<any>(this._constant.baseAppUrl + '/team/createTeam',{country, name}).pipe(
         map(
            data => {
             this.updateHeader.emit("Create Team");
             return Object.assign(new Team(), data);}
          )
       );
  }

  public getBalance(){
    return this.httpClient.get<any>(this._constant.baseAppUrl + '/team/getBalance');
  }

  public getNotifications(){
    return this.httpClient.get<any>(this._constant.baseAppUrl + '/team/getNotifications');
  }

  public deleteNotification(id){
    return this.httpClient.delete<any>(this._constant.baseAppUrl + '/team/deleteNotification/'+id);
  }

  public getPlayersFromTransferList(){
    return this.httpClient.get<any>(this._constant.baseAppUrl + '/team/getTransferList');
  }
}
