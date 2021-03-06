import { Injectable, EventEmitter, Output } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { ConstantsService} from '../common/services/constants.service'
export class User {
  constructor(
    public status: string,
  ) { }

}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  @Output() updateHeader: EventEmitter<any> = new EventEmitter();
  @Output() unsubscribeEvent: EventEmitter<any> = new EventEmitter();
  constructor(
    private httpClient: HttpClient, private _constant: ConstantsService
  ) {
  }

   authenticate(username, password) {
        return this.httpClient.post<any>('http://localhost:8080/authenticate',{username,password}).pipe(
         map(
           userData => {
            sessionStorage.setItem('username',username);
            let tokenStr= 'Bearer '+userData.token;
            sessionStorage.setItem('token', tokenStr);
            sessionStorage.setItem('authorities',JSON.stringify(userData.roles));
            this.updateHeader.emit('Sign In');
            return userData;
           }
         )

        );
      }

  register(email, firstName, lastName, password, reTypedPassword, registerAsGuest){
     return this.httpClient.post<any>(this._constant.baseAppUrl + '/register',{email, firstName, lastName,password, reTypedPassword, registerAsGuest}).pipe(
      map(
        data => {
          console.log(data);
        }
      )
     );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    return !(user === null)
  }

  canAccess(route) {
    let authorities = JSON.parse(sessionStorage.getItem('authorities'));
    let necessaryAuth = route.url.split("/")[1]
    for(let i = 0; i < authorities.length; i++){
      if(authorities[i]["authority"].toUpperCase() === necessaryAuth.toUpperCase())
        return true;
    }
    return false;
  }

  logOut() {
    this.unsubscribeEvent.emit("Unsubscribe")
    sessionStorage.removeItem('username')
    sessionStorage.removeItem('authorities')
  }
}
