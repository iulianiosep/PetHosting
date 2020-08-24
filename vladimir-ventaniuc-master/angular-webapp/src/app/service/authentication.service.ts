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

  register(email, firstName, lastName, password, reTypedPassword){
     return this.httpClient.post<any>(this._constant.baseAppUrl + '/register',{email, firstName, lastName,password, reTypedPassword}).pipe(
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

  logOut() {
    this.unsubscribeEvent.emit("Unsubscribe")
    sessionStorage.removeItem('username')
    sessionStorage.removeItem('authorities')
  }
}
