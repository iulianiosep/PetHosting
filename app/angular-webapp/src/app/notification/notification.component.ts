import { Component, OnInit } from '@angular/core';
import { TeamService } from '../service/teamService/team.service';
import {WebSocketServiceService} from "../service/webSocketService/web-socket-service.service";
import {AuthenticationService} from "../service/authentication.service";
@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {
  showNotification = false;
  stompClient;
  notifications = Array<any>();
  constructor(private teamService : TeamService, private webSocketService: WebSocketServiceService, private authService : AuthenticationService) {
    this.teamService.getUserId.subscribe(result => {
        this.connectToWebSocket(result);
        this.getNotifications();
       });
    this.authService.unsubscribeEvent.subscribe(result => {
        this.notifications = Array<any>();
        this.disconnectWebSocket();
    })
  }
  ngOnInit() {
  }

// Open connection with server socket
  connectToWebSocket(userId){
      if(this.stompClient==undefined || this.stompClient==null){
      this.stompClient = this.webSocketService.connect();

      this.stompClient.connect({}, frame => {

  			// Subscribe to notification topic
      this.stompClient.subscribe('/topic/notification/'+userId, notifications => {
      debugger;
      this.teamService.updateHeaderComponent();
  		console.log(JSON.parse(notifications.body));
      this.notifications.push(JSON.parse(notifications.body));
              })
          });
      }
  }
  disconnectWebSocket(){
      this.stompClient.disconnect();
      this.stompClient = null;
  }
  closeAlert(index, notification) {
      this.notifications.splice(index,1);
      debugger;
      this.teamService.deleteNotification(notification.id).subscribe(
        result=>console.log(result),
        err => console.log(err)
      )
    }
  getNotifications(){
    this.teamService.getNotifications().subscribe(result => this.notifications = result);
  }
}
