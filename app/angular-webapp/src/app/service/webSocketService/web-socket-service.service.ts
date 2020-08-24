import { Injectable } from '@angular/core';
import * as StompJs from '@stomp/stompjs';
//import * as SockJS from 'sockjs-client';
import SockJS from 'sockjs-client';
@Injectable({
  providedIn: 'root'
})
export class WebSocketServiceService {

  constructor() { }
  // Open connection with the back-end socket
  public connect() {

      let socket = new SockJS(`http://localhost:8080/socket`);

      let stompClient = StompJs.Stomp.over(socket);

      return stompClient;

   }

}
