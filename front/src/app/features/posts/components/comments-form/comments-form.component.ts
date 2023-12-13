
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { CommentApiService } from '../../services/comment-api.service';
import { Comment } from '../../../posts/interfaces/comment.interface';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';

@Component({
    selector: 'app-comments-form',
    templateUrl: './comments-form.component.html',
    styleUrls: ['./comments-form.component.scss']
})
export class CommentsFormComponent implements OnInit {

    public commentForm: FormGroup | undefined;
    public postId: string;

    public userName: string | undefined;
    public postTitle: string | undefined;
    
    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private commentApiService: CommentApiService,
        private postApiService: PostApiService,
        private sessionService: SessionService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.userName = this.sessionService.sessionInformation!.userName;
        this.postId = this.route.snapshot.paramMap.get('id')!;
    }

    public ngOnInit(): void {

        this.postApiService
            .detail(this.route.snapshot.paramMap.get('id')!)
            .subscribe((post: Post) => {
                this.postTitle = post.title;
            });

        this.initForm();
    }

    private initForm(): void {

        this.commentForm = this.fb.group({
            content: [
                '',
                [
                Validators.required,
                Validators.max(2000)
                ]
            ],
        });
    }

    public submit(): void {

        const comment = this.commentForm?.value as Comment;
        if (this.postTitle)
            comment.post_title = this.postTitle;
        if (this.userName)
            comment.user_name = this.userName;

        this.commentApiService
            .create(comment)
            .subscribe((_: Comment) => this.exitPage('Commentaire envoyé !'));
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate([`articles`]);
    }
}
