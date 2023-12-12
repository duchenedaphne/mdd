
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';
import { TopicApiService } from 'src/app/features/topics/services/topic-api.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

    public postForm: FormGroup | undefined;
    public topics$ = this.topicApiService.all();
    public user: User | undefined;
    public userName: string | undefined;

    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private postApiService: PostApiService,
        private userService: UserService,
        private sessionService: SessionService,
        private topicApiService: TopicApiService,
        private router: Router
    ) {
    }

    public ngOnInit(): void {

        this.userService
            .getById(this.sessionService.sessionInformation!.id.toString())
            .subscribe((user: User) => { 
                this.user = user;
                this.userName = user.userName;
            });

        this.initForm();
    }

    public submit(): void {

        const post = this.postForm?.value as Post;
        if(this.userName)
            post.user_name = this.userName;

        this.postApiService
            .create(post)
            .subscribe((_: Post) => this.exitPage('Article publié !'));
    }

    private initForm(post?: Post): void {
        
        this.postForm = this.fb.group({
            topic_id: [
                post ? post.topic_name : '',
                [Validators.required]
            ],
            title: [
                post ? post.title : '',
                [Validators.required]
            ],
            content: [
                post ? new Date(post.content).toISOString().split('T')[0] : '',
                [Validators.required]
            ],
        });
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate(['articles']);
    }
}
