import { Component, OnInit, ViewChild } from '@angular/core';
import { TeamService } from '../service/teamService/team.service';
import {Player} from '../shared/models/player';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { PlayerService } from '../service/playerService/player.service';
import { TransferListService } from '../service/transferListService/transfer-list.service';

@Component({
  selector: 'app-search-players',
  templateUrl: './search-players.component.html',
  styleUrls: ['./search-players.component.css']
})
export class SearchPlayersComponent implements OnInit {

  players : Array<Player>;
  displayedColumns: string[] = ['firstName', 'lastName', 'country', 'age','position','teamName','value','price','buy'];
  dataSource = new MatTableDataSource(this.players);

  firstNameSearch: string ='';
  lastNameSearch: string ='';
  countrySearch: string = '';
  positionSearch: string = '';
  teamName: string = '';
  maxValue: number = 99999999999;
  minValue: number = 0;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private transferListService:TransferListService) { }

  ngOnInit() {
    this.getTransferList();
  }
  getTransferList(){
    debugger;
      this.transferListService.getPlayersFromTransferList(this.firstNameSearch, this.lastNameSearch, this.countrySearch, this.teamName, this.minValue, this.maxValue).subscribe(
        response => {
           debugger;
           this.players = response;
           this.dataSource = new MatTableDataSource(this.players);
           this.dataSource.sort = this.sort;
           }
         )
    }
    buyPlayer(player : Player){

      this.transferListService.buyPlayer(player.id).subscribe(
        response => {
            debugger;
            this.findAndDeletePlayerFromArray(player.id);
        },
        err => alert(err.error)
      )
    }
    findAndDeletePlayerFromArray(playerId){
        debugger;
        let index = this.players.findIndex( player => player.id === playerId );
        this.players.splice(index,1);
        this.dataSource = new MatTableDataSource(this.players);
        this.dataSource.sort = this.sort;
      }

}
