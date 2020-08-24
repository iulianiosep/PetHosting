import { Component, OnInit, ViewChild } from '@angular/core';
import{ AdminService} from '../service/adminService/admin.service';
import { User } from '../shared/models/user';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import {MatDialog } from '@angular/material';
import {EditUserComponent} from './edit-user/edit-user.component';
@Component({
  selector: 'app-admin-user-management',
  templateUrl: './admin-user-management.component.html',
  styleUrls: ['./admin-user-management.component.css']
})
export class AdminUserManagementComponent implements OnInit {

  users : Array<User>
  displayedColumns: string[] = ['email', 'firstName','lastName','roles','edit','delete'];
  dataSource = new MatTableDataSource(this.users);

  @ViewChild(MatSort) sort: MatSort;
  constructor(
                public dialog: MatDialog,
                private adminService : AdminService) { }

  ngOnInit() {
        this.dataSource.sort = this.sort;
        this.getUsers();
  }

  getUsers(){
    this.adminService.getUsers().subscribe(
             response => {
             debugger;
                        this.users = response;
                        this.dataSource = new MatTableDataSource(this.users);
                        this.dataSource.sort = this.sort;
                         }
              )
  }

  deleteUser(user : User){
    this.adminService.deleteUser(user.id).subscribe(
        response=>this.findAndDeleteUserFromArray(user.id),
        err => console.log(err)
    );
  }

  findAndDeleteUserFromArray(userId){
         let index = this.users.findIndex( user => user.id === userId );
         this.users.splice(index,1);
         this.dataSource = new MatTableDataSource(this.users);
         this.dataSource.sort = this.sort;
      }

  openModal(user: User): void {
            const dialogRef = this.dialog.open(EditUserComponent, {
              width: '400px',
              backdropClass:'custom-dialog-backdrop-class',
              panelClass:'custom-dialog-panel-class',
              data: {user: user}
            });

            dialogRef.afterClosed().subscribe(result => {
              if(result.event === 'submit'){
                debugger;
                this.adminService.modifyUser(result.data, result.password).subscribe(
                  response => this.copyProperties(user, result.data),
                  err => console.log(err)
                )

              }
            });
          }
   copyProperties(user:User, newUser:User){
           user.firstName = newUser.firstName;
           user.lastName = newUser.lastName;
           user.email = newUser.email;
           user.roles = newUser.roles;
       }
}
