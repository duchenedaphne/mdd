
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

    public topics$: Observable<Topic[]>; 
    public isSubscribe = true; 

    public user: User | undefined;
    public userName: string | undefined;
    public email: string | undefined;
    public createdAt: Date | undefined;
    public updatedAt: Date | undefined;
    public userId: number | undefined;
  
    public onError = false;

    public form = this.fb.group({
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

    constructor(
        private router: Router,
        private sessionService: SessionService,
        private matSnackBar: MatSnackBar,
        private userService: UserService,
        private topicApiService: TopicApiService,
        private fb: FormBuilder
    ) {   
        this.userId = this.sessionService.sessionInformation!.id;
        this.topics$ = this.topicApiService.allByUserId(this.userId.toString());
    }

    ngOnInit(): void {
        this.userService
            .getById(this.sessionService.sessionInformation!.id.toString())
            .subscribe((user: User) =>{ 
                    this.user = user;
                    this.userName = user.userName;
                    this.email = user.email;
                    this.createdAt = user.createdAt;
                    this.updatedAt = user.updatedAt;
                }
            );
    }

    public submit(): void {
        const userUpdated = this.form.value as User;
        
        this.userService
            .update(this.sessionService.sessionInformation!.id.toString(), userUpdated)
            .subscribe({
                next: (_: void) => {
                    this.matSnackBar.open("Votre compte a été modifié !", 'Close', { duration: 3000 }); 
                    this.router.navigate(['/me']);
                },
                error: _ => this.onError = true,
            });
    }

    public logout(): void {
      this.sessionService.logOut();
      this.router.navigate([''])
    }

    public unSubscribe(id:number): void {
      this.topicApiService
        .unSubscribe(id.toString(), this.sessionService.sessionInformation!.id.toString())
        .subscribe(_ => this.fetchTopic(id));
    }

    private fetchTopic(id:number): void {
  
      this.topicApiService
        .detail(id.toString())
        .subscribe((topic: Topic) => {
          this.isSubscribe = topic.users.some(u => u === this.sessionService.sessionInformation!.id);
        });
    }
}
