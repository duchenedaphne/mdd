
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { DetailComponent } from './components/detail/detail.component';
import { FormComponent } from './components/form/form.component';

const routes: Routes = [
  
  { path: '',
    canActivate: [AuthGuard],
    component: ListComponent
  },
  { path: 'create',
    canActivate: [AuthGuard],
    component: FormComponent
  },  
  { path: 'detail/:id',
    canActivate: [AuthGuard],
    component: DetailComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule { }
