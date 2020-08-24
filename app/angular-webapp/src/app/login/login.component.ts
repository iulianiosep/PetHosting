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
  isUser = false;
  invalidData = false;
  constructor(private router: Router,
    private loginservice: AuthenticationService) { }

  ngOnInit() {
  }

  checkLogin() {
    (this.loginservice.authenticate(this.username, this.password).subscribe(
      data => {
        this.getRoles();

        if(this.isUser){
          this.router.navigate(['/team'])
        }
        else if(this.isLeagueManager){
          this.router.navigate(['/leagueManager'])
        }
        else if(this.isAdmin){
          this.router.navigate(['/admin'])
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
                   if(entry.authority === 'LeagueManager'){
                      this.isLeagueManager = true;
                   }
                   if(entry.authority === 'User'){
                      this.isUser = true;
                   }
        });
    }

}
