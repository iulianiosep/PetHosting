import { Component, OnInit } from '@angular/core';
import { TeamService } from '../service/teamService/team.service';
import { PlayerService } from '../service/playerService/player.service';
import { Router } from '@angular/router';
import {Team} from '../shared/models/team';
import {User} from '../shared/models/user';
import {Player} from '../shared/models/player';
import  {MatDialog } from '@angular/material';
import { PlayerModalComponent } from './player-modal/player-modal.component';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  team : Team = new Team();
  goalkeeperFilter = {position: 'GOALKEEPER'};
  defenderFilter = {position: 'DEFENDER'};
  midfilderFilter = {position: 'MIDFIELDER'};
  attackerFilter = {position: 'ATTACKER'};
  dialogValue:string;
  sendValue:string;

  get getNumberAttackers() {
  if(this.team.players === undefined)
    return 0;
  else
    return this.team.players.filter(item => item.position === this.attackerFilter.position).length;
  }
  get getNumberGoalkeepers() {
    if(this.team.players === undefined)
      return 0;
    else
      return this.team.players.filter(item => item.position === this.goalkeeperFilter.position).length;
    }

  constructor(private router: Router,private teamService:TeamService, public dialog: MatDialog,private playerService:PlayerService) { }

  ngOnInit() {

    this.teamService.getTeam().subscribe(
      response => {
        this.team = response;
        },
      err=> this.router.navigateByUrl('/createteam')
    )
  }
  openModal(player: Player): void {
      const dialogRef = this.dialog.open(PlayerModalComponent, {
        width: '400px',
        backdropClass:'custom-dialog-backdrop-class',
        panelClass:'custom-dialog-panel-class',
        data: {player: player}
      });

      dialogRef.afterClosed().subscribe(result => {
        if(result.event === 'submit'){
          this.submitPlayerToTransferList(player, result.data);
        }
      });
    }

  submitPlayerToTransferList(player : Player, value : number){
    this.playerService.submitPlayerToTransferList(player.id, value).subscribe(
      response =>{
        player.onTransferList = true;
      },
      err =>{
        console.log(err);
      }
    )
  }
  removePlayerFromTransferList(player: Player){
    this.playerService.removePlayerFromTransferList(player.id).subscribe(
     response =>{
                 player.onTransferList = false;
     },
     err =>{
             console.log(err);
            }
      )
  }
}
