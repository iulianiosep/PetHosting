import { Component, OnInit, ViewChild } from '@angular/core';
import { LeagueManagerService } from '../service/leagueManagerService/league-manager.service';
import{ AdminService} from '../service/adminService/admin.service';
import { Team } from '../shared/models/team';
import { Player } from '../shared/models/player';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import {MatDialog } from '@angular/material';
import {EditTeamAdminComponent} from './edit-team/edit-team.component';
import {AddPlayerComponent} from './add-player/add-player.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  teams : Array<Team>
  displayedColumns: string[] = ['teamName', 'country','value','balance','players','edit','add','delete'];
  dataSource = new MatTableDataSource(this.teams);

  @ViewChild(MatSort) sort: MatSort;
  constructor(private leagueManagerService : LeagueManagerService,
              private router:Router,
              public dialog: MatDialog,
              private adminService : AdminService) { }

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
  openPlayerComponent(team : Team){
    this.router.navigateByUrl('/admin/player', { state: { players: team.players, teamName: team.name } });
  }
   openModal(team: Team): void {
          const dialogRef = this.dialog.open(EditTeamAdminComponent, {
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

   addPlayer(team: Team): void {
        const dialogRef = this.dialog.open(AddPlayerComponent, {
                 width: '400px',
                 backdropClass:'custom-dialog-backdrop-class',
                 panelClass:'custom-dialog-panel-class',
                 data: {team: team}
         });

        dialogRef.afterClosed().subscribe(result => {
                 if(result.event === 'submit'){
                   this.adminService.createPlayer(result.data, team.id).subscribe(
                        result=>{
                          team.value = team.value + result.value;
                          team.players.push(result);
                        },
                        err => console.log(err)
                        );
                 }
               });
         }


}
