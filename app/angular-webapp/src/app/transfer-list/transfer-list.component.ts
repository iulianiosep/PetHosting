import { Component, OnInit, ViewChild } from '@angular/core';
import { TeamService } from '../service/teamService/team.service';
import {Player} from '../shared/models/player';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { PlayerService } from '../service/playerService/player.service';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent implements OnInit {
  players : Array<Player>;
  displayedColumns: string[] = ['firstName', 'lastName', 'country', 'age','position','value','price', 'delete'];
  dataSource = new MatTableDataSource(this.players);

    @ViewChild(MatSort) sort: MatSort;

  constructor(private teamService:TeamService, private playerService:PlayerService) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;
    this.getTransferList();
  }

  getTransferList(){
  debugger;
    this.teamService.getPlayersFromTransferList().subscribe(
      response => {
         this.players = response;
         this.dataSource = new MatTableDataSource(this.players);
         this.dataSource.sort = this.sort;
         }
       )
  }
  removeFromTransferList(player : Player, index : number){
   this.playerService.removePlayerFromTransferList(player.id).subscribe(
        response =>{
                    this.findAndDeletePlayerFromArray(player.id);
        },
        err =>{
                console.log(err);
               }
         )
  }
  findAndDeletePlayerFromArray(playerId){
    let index = this.players.findIndex( player => player.id === playerId );
    this.players.splice(index,1);
    this.dataSource = new MatTableDataSource(this.players);
    this.dataSource.sort = this.sort;
  }

}
