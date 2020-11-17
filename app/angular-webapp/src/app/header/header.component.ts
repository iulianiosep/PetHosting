import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { TeamService } from '../service/teamService/team.service';
import { TransferListService} from '../service/transferListService/transfer-list.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  balance:0;
  teamName:'';
  isAdmin = false;
  isLeagueManager = false;
  isUser = false;
  constructor(private authenticationService:AuthenticationService, private teamService:TeamService, private transferListService : TransferListService){
    this.authenticationService.updateHeader.subscribe(result => {
    this.resetRoles();
    this.getRoles();
    this.getBalance();
   });
   this.transferListService.getUserBought.subscribe(result => {
    this.getBalance();
   });
   this.teamService.updateHeader.subscribe(result => {
    this.getBalance();
   })
   }

  ngOnInit() {
    this.getRoles();
    this.getBalance();
  }
  getBalance(){
    this.teamService.getBalance().subscribe(
      response=>{

        this.balance = response.balance;
        this.teamName = response.teamName
      }
    )
  }
  getRoles(){
  let authorities = JSON.parse(sessionStorage.getItem('authorities'));
  if(authorities != null){
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
  resetRoles(){
    this.isUser = false;
    this.isAdmin = false;
    this.isLeagueManager = false;
  }

  logOut(){
    this.authenticationService.logOut();
  }

}
