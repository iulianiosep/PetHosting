import { Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {Player} from '../../shared/models/player';
@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.css']
})
export class AddPlayerComponent implements OnInit {


  player: Player;

  constructor( public dialogRef: MatDialogRef<AddPlayerComponent>, @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
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
