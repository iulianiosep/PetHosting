import { Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {Player} from '../../../shared/models/player';
@Component({
  selector: 'app-edit-player',
  templateUrl: './edit-player.component.html',
  styleUrls: ['./edit-player.component.css']
})
export class EditPlayerComponent implements OnInit {

  player: Player;
  fromPage ='';
  fromDialog ='';
  marketValue : number;

  constructor( public dialogRef: MatDialogRef<EditPlayerComponent>, @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
          this.player = Object.assign({}, data.player);
  }

  ngOnInit() {  }

  closeDialog(){
        this.dialogRef.close({event:'close',data:0});
      }
  submitDialog(){
        this.dialogRef.close({event:'submit',data:this.player});
      }

}
