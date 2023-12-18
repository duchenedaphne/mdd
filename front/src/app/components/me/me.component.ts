
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import { Validators, FormBuilder } from '@angular/forms';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { TopicApiService } from 'src/app/features/topics/services/topic-api.service';

@Component({
    selector: 'app-me',
    templateUrl: './me.component.html',
    styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

    public topics$: Observable<Topic[]> | undefined; 
    public isSubscribe = true; 
    
    public userName: string;
    public email: string;
    public userId: number | undefined;
    
    public meForm = this.fb.group({
        email: [
            '',
            [
                Validators.required,
                Validators.email
            ]
        ],
        userName: [
            '',
            [
                Validators.required,
                Validators.min(3),
                Validators.max(20)
            ]
        ]
    });
    public onError = false;

    constructor(
        private router: Router,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
        private userService: UserService,
        private topicApiService: TopicApiService,
        private fb: FormBuilder
    ) { 
        this.userName = this.sessionService.sessionInformation!.userName;
        this.email = this.sessionService.sessionInformation!.email; 
        
        this.meForm.setValue({
            email: this.email,
            userName: this.userName
        });
    }

    ngOnInit(): void {
        this.userId = this.sessionService.sessionInformation!.id;
        this.topics$ = this.topicApiService.allByUserId(this.userId.toString());
    }

    public submit(): void {
        
        const userUpdated = this.meForm.value as User;
        
        if (userUpdated.email != this.email || userUpdated.userName != this.userName)
            this.userService
                .update(this.sessionService.sessionInformation!.id.toString(), userUpdated)
                .subscribe({
                    next: (_: void) => {
                        this.matSnackBar.open("Votre compte a été modifié, veuillez vous reconnecter !", 'Close', { duration: 3000 }); 
                        this.sessionService.logOut();
                        this.router.navigate(['/fr/login']);
                    },
                    error: _ => this.onError = true,
                });
        else
            alert('aucune modification détectée.')
    }

    public logout(): void {
      this.sessionService.logOut();
      this.router.navigate([''])
    }

    public unSubscribe(id:number): void {
      this.topicApiService
        .unSubscribe(id.toString(), this.sessionService.sessionInformation!.id.toString())
        .subscribe(_ => this.fetchTopic(id));

        this.router.navigate(['articles']);
    }

    private fetchTopic(id:number): void {
  
      this.topicApiService
        .detail(id.toString())
        .subscribe((topic: Topic) => {
          this.isSubscribe = topic.users.some(u => u === this.sessionService.sessionInformation!.id);
        });
    }
}
