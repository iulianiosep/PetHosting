import { Component } from '@angular/core';
import {WebSocketServiceService} from "./service/webSocketService/web-socket-service.service";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'employee-management';
  public notifications = 0;

      constructor(private webSocketService: WebSocketServiceService) {
      /*
  		// Open connection with server socket
      let stompClient = this.webSocketService.connect();

      stompClient.connect({}, frame => {

  			// Subscribe to notification topic
      stompClient.subscribe('/topic/notification', notifications => {

  				// Update notifications attribute with the recent messsage sent from the server
      this.notifications = JSON.parse(notifications.body).count;
              })
          });
    */
      }
}
