import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false
  isLeagueManager = false;
  isAdmin = false;
  isGuest = false;
  isHost = false;
  invalidData = false;
  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  ngOnInit() {
  }

  checkLogin() {
    (this.loginservice.authenticate(this.username, this.password).subscribe(
      data => {
        this.getRoles();

        if(this.isAdmin){
          this.router.navigate(['/admin'])
        }
        else if(this.isGuest){
          this.router.navigate(['/guest'])
        }
        else if(this.isHost){
          this.router.navigate(['/host'])
        }
        this.invalidLogin = false;
      },
      error => {
        this.invalidLogin = true;
        this.username ='';
        this.password ='';
        this.invalidData = true;
      }
    )
    );

  }
  getRoles(){
    let authorities = JSON.parse(sessionStorage.getItem('authorities'));

        authorities.forEach(entry => {
                   if(entry.authority === 'Admin'){
                      this.isAdmin = true;
                   }
                   if(entry.authority === 'Guest'){
                      this.isGuest = true;
                   }
                   if(entry.authority === 'Host'){
                      this.isHost = true;
                   }
        });
    }

}
