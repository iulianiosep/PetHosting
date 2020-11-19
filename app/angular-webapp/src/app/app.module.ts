import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EmployeeComponent } from './employee/employee.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddEmployeeComponent } from './add-employee/add-employee.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { BasicAuthHtppInterceptorService } from './service/basic-auth-htpp-interceptor.service';
import { RegisterComponent } from './register/register.component';
import { ConstantsService } from './common/services/constants.service';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { TeamComponent } from './team/team.component';
import { CreateTeamComponent } from './team/create-team/create-team.component';
import { PositionFilterPipe } from './shared/filters/position-filter.pipe';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { PlayerModalComponent } from './team/player-modal/player-modal.component';
import { TransferListComponent } from './transfer-list/transfer-list.component';
import { SearchPlayersComponent } from './search-players/search-players.component';
import {WebSocketServiceService} from "./service/webSocketService/web-socket-service.service";
import { NotificationComponent } from './notification/notification.component';
import { NotifierModule } from "angular-notifier";
import { LeagueManagerComponent } from './league-manager/league-manager.component';
import { EditTeamComponent } from './league-manager/edit-team/edit-team.component';
import { AdminComponent } from './admin/admin.component';
import { PlayerComponent } from './admin/player/player.component';
import { StateService } from '@uirouter/core';
import { EditPlayerComponent } from './admin/player/edit-player/edit-player.component';
import { EditTeamAdminComponent } from './admin/edit-team/edit-team.component';
import { AddPlayerComponent } from './admin/add-player/add-player.component';
import { AdminUserManagementComponent } from './admin-user-management/admin-user-management.component';
import { EditUserComponent } from './admin-user-management/edit-user/edit-user.component';
import { HomePageComponent } from './home-page/home-page.component';
import { GuestComponent } from './guest/guest.component';
import { HostComponent } from './host/host.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    AddEmployeeComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    TeamComponent,
    CreateTeamComponent,
    PositionFilterPipe,
    PlayerModalComponent,
    TransferListComponent,
    SearchPlayersComponent,
    NotificationComponent,
    LeagueManagerComponent,
    EditTeamComponent,
    AdminComponent,
    PlayerComponent,
    EditPlayerComponent,
    EditTeamAdminComponent,
    AddPlayerComponent,
    AdminUserManagementComponent,
    EditUserComponent,
    HomePageComponent,
    GuestComponent,
    HostComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    FormsModule,
    MaterialModule,
    NotifierModule
  ],
  providers: [
  {
    provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService, multi:true
  }, ConstantsService, WebSocketServiceService
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    PlayerModalComponent,
    EditTeamComponent,
    EditPlayerComponent,
    EditTeamAdminComponent,
    AddPlayerComponent,
    EditUserComponent
  ]
})
export class AppModule { }
