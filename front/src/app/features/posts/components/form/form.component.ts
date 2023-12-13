
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';
import { TopicApiService } from 'src/app/features/topics/services/topic-api.service';

@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

    public postForm: FormGroup | undefined;
    public topics$ = this.topicApiService.all();
    public userName: string | undefined;

    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private postApiService: PostApiService,
        private sessionService: SessionService,
        private topicApiService: TopicApiService,
        private router: Router
    ) {
        this.userName = this.sessionService.sessionInformation!.userName;
    }

    public ngOnInit(): void {
        this.initForm();
    }

    private initForm(): void {
        
        this.postForm = this.fb.group({
            topic_name: [
                '',
                [Validators.required]
            ],
            title: [
                '',
                [Validators.required]
            ],
            content: [
                '',
                [Validators.required]
            ],
        });
    }

    public submit(): void {

        const post = this.postForm?.value as Post;
        if(this.userName)
            post.user_name = this.userName;

        this.postApiService
            .create(post)
            .subscribe((_: Post) => this.exitPage('Article publié !'));
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate(['articles']);
    }
}
