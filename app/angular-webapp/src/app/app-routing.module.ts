import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeComponent } from './employee/employee.component';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthGaurdService } from './service/auth-gaurd.service';
import { RegisterComponent } from './register/register.component';
import {TeamComponent} from './team/team.component';
import {CreateTeamComponent} from './team/create-team/create-team.component';
import { TransferListComponent } from './transfer-list/transfer-list.component';
import { SearchPlayersComponent } from './search-players/search-players.component';
import { LeagueManagerComponent } from './league-manager/league-manager.component';
import { AdminComponent } from './admin/admin.component';
import { PlayerComponent } from './admin/player/player.component';
import { AdminUserManagementComponent } from './admin-user-management/admin-user-management.component';
import { HomePageComponent } from './home-page/home-page.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  //{ path: '', component: TeamComponent,canActivate:[AuthGaurdService] },
  { path: 'leagueManager', component : LeagueManagerComponent, canActivate:[AuthGaurdService]},
  { path: 'admin', component: AdminComponent, canActivate:[AuthGaurdService]},
  { path: 'userManagement', component: AdminUserManagementComponent, canActivate:[AuthGaurdService]},
  { path: 'admin/player', component: PlayerComponent, canActivate:[AuthGaurdService]},
  { path:'team', component: TeamComponent, canActivate:[AuthGaurdService]},
  { path:'createteam', component: CreateTeamComponent, canActivate:[AuthGaurdService]},
  { path: 'addemployee', component: AddEmployeeComponent,canActivate:[AuthGaurdService]},
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent,canActivate:[AuthGaurdService] },
  { path: 'register', component: RegisterComponent},
  { path: 'transferlist', component: TransferListComponent, canActivate:[AuthGaurdService]},
  { path: 'searchplayers', component: SearchPlayersComponent, canActivate:[AuthGaurdService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
