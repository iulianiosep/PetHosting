import { Component, OnInit, ViewChild } from '@angular/core';
import { LeagueManagerService } from '../service/leagueManagerService/league-manager.service';
import {Team} from '../shared/models/team';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import  {MatDialog } from '@angular/material';
import { EditTeamComponent } from './edit-team/edit-team.component';
@Component({
  selector: 'app-league-manager',
  templateUrl: './league-manager.component.html',
  styleUrls: ['./league-manager.component.css']
})
export class LeagueManagerComponent implements OnInit {
  teams : Array<Team>
  displayedColumns: string[] = ['teamName', 'country','value','balance','edit','delete'];
  dataSource = new MatTableDataSource(this.teams);

  @ViewChild(MatSort) sort: MatSort;

  constructor(private leagueManagerService : LeagueManagerService, public dialog: MatDialog) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.getTeams();
  }
  getTeams(){
    this.leagueManagerService.getTeams().subscribe(
       response => {
                  this.teams = response;
                  this.dataSource = new MatTableDataSource(this.teams);
                  this.dataSource.sort = this.sort;
                   }
        )
  }
  openModal(team: Team): void {
        const dialogRef = this.dialog.open(EditTeamComponent, {
          width: '400px',
          backdropClass:'custom-dialog-backdrop-class',
          panelClass:'custom-dialog-panel-class',
          data: {team: team}
        });

        dialogRef.afterClosed().subscribe(result => {
          if(result.event === 'submit'){
            this.copyProperties(team, result.data)
            this.leagueManagerService.updateTeam(team.id, team.name, team.country, team.balance).subscribe(
                       result => console.log(result),
                       err =>console.log(err)
             )

          }
        });
      }
  copyProperties(team:Team, newTeam:Team){
     team.name = newTeam.name;
     team.country = newTeam.country;
     team.balance = newTeam.balance;
  }

  removeTeam(team : Team){
        this.leagueManagerService.deleteTeam(team.id).subscribe(
             response =>{
                         this.findAndDeleteTeamFromArray(team.id);
             },
             err =>{
                     console.log(err);
                    }
              )
  }

  findAndDeleteTeamFromArray(teamId){
         let index = this.teams.findIndex( team => team.id === teamId );
         this.teams.splice(index,1);
         this.dataSource = new MatTableDataSource(this.teams);
         this.dataSource.sort = this.sort;
  }
}
