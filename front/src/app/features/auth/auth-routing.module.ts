
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UnauthGuard } from 'src/app/guards/unauth.guard';


const routes: Routes = [
  { 
    title: 'Login', 
    path: 'login',
    canActivate: [UnauthGuard],
    component: LoginComponent 
  },
  { 
    title: 'Register', 
    path: 'register',
    canActivate: [UnauthGuard], 
    component: RegisterComponent 
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
