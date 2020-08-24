import { Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {Team} from '../../shared/models/team';
@Component({
  selector: 'app-edit-team',
  templateUrl: './edit-team.component.html',
  styleUrls: ['./edit-team.component.css']
})
export class EditTeamAdminComponent implements OnInit {

  team: Team;

  constructor( public dialogRef: MatDialogRef<EditTeamAdminComponent>, @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
            this.team = Object.assign({}, data.team);
    }

  ngOnInit() {  }

  closeDialog(){
          this.dialogRef.close({event:'close',data:0});
        }
    submitDialog(){
          this.dialogRef.close({event:'submit',data:this.team});
        }


}
