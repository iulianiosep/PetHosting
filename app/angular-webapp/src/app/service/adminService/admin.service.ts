  import { Injectable } from '@angular/core';
  import { HttpClient, HttpHeaders } from '@angular/common/http';
  import { ConstantsService} from '../../common/services/constants.service';
  import { map } from 'rxjs/operators';
  import {Player} from '../../shared/models/player';
  import {User} from '../../shared/models/user';
  @Injectable({
    providedIn: 'root'
  })
  export class AdminService {

    constructor(private httpClient: HttpClient,
                private _constant: ConstantsService) { }
    public updatePlayer(id, firstName, lastName, country, age, playerPosition, value){
          return this.httpClient.put<any>(this._constant.baseAppUrl + '/admin/modifyPlayer',{id, firstName, lastName, country,age,playerPosition,value});
      }
    public deletePlayer(playerId){
          return this.httpClient.delete<any>(this._constant.baseAppUrl + '/admin/deletePlayer/'+playerId);
    }
    public createPlayer(player : Player, teamId){
          return this.httpClient.post<any>(this._constant.baseAppUrl + '/admin/createPlayer',{
                teamId:teamId,
                firstName:player.firstName,
                lastName:player.lastName,
                country:player.country,
                age:player.age,
                value:player.value,
                position:player.position
          });
    }

    public getUsers(){
       return this.httpClient.get<any>(this._constant.baseAppUrl + '/admin/getUsers');
    }

    public deleteUser(userId){
       return this.httpClient.delete<any>(this._constant.baseAppUrl + '/admin/deleteUser/'+userId);
    }

    public modifyUser(user:User, password){
    debugger
        return this.httpClient.put<any>(this._constant.baseAppUrl + '/admin/modifyUser',{
            id:user.id,
            email:user.email,
            firstName:user.firstName,
            lastName:user.lastName,
            password:password,
            roles:user.roles
        });
    }


  }
