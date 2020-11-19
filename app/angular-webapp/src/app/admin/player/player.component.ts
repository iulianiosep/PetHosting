import {ActivatedRoute} from '@angular/router';
import { map } from 'rxjs/operators';
import {Observable} from 'rxjs';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Player } from '../../shared/models/player';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import {MatDialog } from '@angular/material';
import {EditPlayerComponent} from './edit-player/edit-player.component';
import { AdminService } from '../../service/adminService/admin.service';
@Component({
  selector: 'app-player',
  templateUrl: './player.component.html',
  styleUrls: ['./player.component.css']
})
export class PlayerComponent implements OnInit {
  state$: Observable<object>;
  players : Array<Player>;
  teamName : string ='';
  displayedColumns: string[] = ['firstName', 'lastName','country','age','position','value','teamName','edit','delete'];
  dataSource = new MatTableDataSource(this.players);

  @ViewChild(MatSort) sort: MatSort;

  constructor(public activatedRoute: ActivatedRoute, public dialog: MatDialog, private adminService : AdminService) { }

  ngOnInit() {

  this.state$ = this.activatedRoute.paramMap
        .pipe(map(() => {this.players = window.history.state.players;
        console.log(this.players);
        return window.history.state;}))
   this.players = window.history.state.players;
   this.teamName = window.history.state.teamName;
   this.dataSource = new MatTableDataSource(this.players);
   this.dataSource.sort = this.sort;
  }

  openModal(player: Player): void {
        const dialogRef = this.dialog.open(EditPlayerComponent, {
          width: '400px',
          backdropClass:'custom-dialog-backdrop-class',
          panelClass:'custom-dialog-panel-class',
          data: {player: player}
        });

        dialogRef.afterClosed().subscribe(result => {
          if(result.event === 'submit'){
            this.copyProperties(player, result.data);

            this.adminService.updatePlayer(player.id, player.firstName,
                                           player.lastName, player.country,
                                           player.age, player.position,
                                           player.value)
                                           .subscribe(
                                            result => console.log(result),
                                            err =>console.log(err));

        }
        });
        }
   copyProperties(player:Player, newPlayer:Player){
     player.firstName = newPlayer.firstName;
     player.lastName = newPlayer.lastName;
     player.country = newPlayer.country;
     player.age = newPlayer.age;
     player.value = newPlayer.value;
     player.position = newPlayer.position;
   }

  removePlayer(player : Player){
      this.adminService.deletePlayer(player.id).subscribe(
         response =>{
                       this.findAndDeletePlayerFromArray(player.id);
              },
         err =>console.log(err)
               )
  }

  findAndDeletePlayerFromArray(playerId){
       let index = this.players.findIndex( player => player.id === playerId );
       this.players.splice(index,1);
       this.dataSource = new MatTableDataSource(this.players);
       this.dataSource.sort = this.sort;
  }

}
