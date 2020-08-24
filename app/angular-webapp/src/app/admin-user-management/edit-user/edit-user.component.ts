import { Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import {User} from '../../shared/models/user';
@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  user: User;
  password ='';
  isleagueManager = false;
  isUser = false;
  constructor( public dialogRef: MatDialogRef<EditUserComponent>, @Optional() @Inject(MAT_DIALOG_DATA) public data: any) {
              this.user = Object.assign({}, data.user);
              for (var i = 0; i < this.user.roles.length; i++) {
                  if(this.user.roles[i] === 'User'){
                    this.isUser = true;
                  }
                  if(this.user.roles[i] === 'LeagueManager'){
                    this.isleagueManager = true;
                  }

              }
      }f

  ngOnInit() {  }

  closeDialog(){
     this.dialogRef.close({event:'close',data:0});
  }

  submitDialog(){
     let roles = new Array();
     if(this.isUser)
      roles.push("User");
     if(this.isleagueManager)
      roles.push("LeagueManager");
     this.user.roles = roles;
     this.dialogRef.close({event:'submit',data:this.user, password: this.password});
  }

}
