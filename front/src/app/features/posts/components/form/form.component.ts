
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
    public userId: number;

    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private postApiService: PostApiService,
        private sessionService: SessionService,
        private topicApiService: TopicApiService,
        private router: Router
    ) {
        this.userId = this.sessionService.sessionInformation!.id;
    }

    public ngOnInit(): void {    
        this.initForm();
    }

    public submit(): void {

        const post = this.postForm?.value as Post;
        post.user_id = this.userId;

        this.postApiService
            .create(post)
            .subscribe((_: Post) => this.exitPage('Article publié !'));
    }

    private initForm(post?: Post): void {
        
        this.postForm = this.fb.group({
            topic_id: [
                post ? post.topic_id : '',
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
