import { Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {Player} from '../../shared/models/player';
@Component({
  selector: 'app-player-modal',
  templateUrl: './player-modal.component.html',
  styleUrls: ['./player-modal.component.css']
})
export class PlayerModalComponent implements OnInit {

  player: Player;
  fromPage ='';
  fromDialog ='';
  marketValue : number;

  get getFirstName() {return this.player.firstName}
    constructor(
      public dialogRef: MatDialogRef<PlayerModalComponent>,
      @Optional() @Inject(MAT_DIALOG_DATA) public data: any
      ) {
        this.player = data.player;
      }

    ngOnInit() {
    }

    closeDialog(){
      this.dialogRef.close({event:'close',data:0});
    }
    submitDialog(){
      this.dialogRef.close({event:'submit',data:this.marketValue});
    }
}
