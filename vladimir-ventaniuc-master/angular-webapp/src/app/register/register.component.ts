import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  firstName='';
  lastName='';
  email='';
  password='';
  password2='';
  submitted = false;
  passwordsDontMatch = false;
  firstNameRequired = false;
  lastNameRequired = false;
  invalidEmail = false;
  userAlreadyExists = false;
  constructor(private router: Router,
                  private registerService: AuthenticationService, private formBuilder: FormBuilder) { }

  ngOnInit() {
  }

checkInputData(){
    if(this.password!= this.password2 || this.password === ''){
      this.passwordsDontMatch = true;
    }
    else{
     this.passwordsDontMatch = false;
    }
    if(this.lastName===''){
     this.lastNameRequired = true;
    }
    else{
      this.lastNameRequired = false;
    }
    if(this.firstName === ''){
      this.firstNameRequired = true;
    }
    else{
      this.firstNameRequired = false;
    }
    if(this.email === '' || ((this.email.indexOf("@") < 0 || this.email.indexOf(".") < 0) || this.email.indexOf("@") > this.email.lastIndexOf("."))){
      this.invalidEmail = true;
    }
    else{
      this.invalidEmail = false;
    }
}

clearFields(){
    this.firstName='';
    this.lastName='';
    this.email='';
    this.password='';
    this.password2='';
}
register() {

    this.checkInputData();
    if(this.invalidEmail || this.firstNameRequired || this.lastNameRequired || this.passwordsDontMatch)
      return;
    (this.registerService.register(this.email, this.firstName, this.lastName, this.password, this.password2).subscribe(
      data => {
        this.router.navigateByUrl('/login')
      },
      error => {
            this.userAlreadyExists = true;
            this.clearFields();
      }
    )
    );

  }
}
