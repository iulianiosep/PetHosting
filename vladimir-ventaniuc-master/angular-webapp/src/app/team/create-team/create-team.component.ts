import { Component, OnInit } from '@angular/core';
import { TeamService } from '../../service/teamService/team.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-create-team',
  templateUrl: './create-team.component.html',
  styleUrls: ['./create-team.component.css']
})
export class CreateTeamComponent implements OnInit {
  country='';
  teamName='';
  submitted = false;
  countryNameRequired = false;
  teamNameRequired = false;
  teamAlreadyExists = false;

  constructor(private router: Router,private teamService:TeamService) { }

  ngOnInit() {
  }

  checkInputData(){
      if(this.country === ''){
        this.countryNameRequired = true;
      }
      else{
       this.countryNameRequired = false;
      }
      if(this.teamName===''){
       this.teamNameRequired = true;
      }
      else{
        this.teamNameRequired = false;
      }
  }

  clearFields(){
      this.country='';
      this.teamName='';
  }
  createTeam() {
      this.checkInputData();
      if(this.countryNameRequired || this.teamNameRequired )
        return;


      (this.teamService.createTeam(this.country, this.teamName).subscribe(
            data => {
              debugger;
              this.router.navigateByUrl('/team')
            },
            error => {
              debugger;
              this.teamAlreadyExists = true;
              this.clearFields();
            }
          )
          );
    }

}
