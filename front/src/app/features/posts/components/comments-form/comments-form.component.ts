
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { CommentApiService } from '../../services/comment-api.service';
import { Comment } from '../../../posts/interfaces/comment.interface';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';

@Component({
    selector: 'app-comments-form',
    templateUrl: './comments-form.component.html',
    styleUrls: ['./comments-form.component.scss']
})
export class CommentsFormComponent implements OnInit {

    public commentForm: FormGroup | undefined;
    public user: User | undefined;
    public post: Post | undefined;
    public postId: string;

    public userName: string | undefined;
    public postTitle: string | undefined;
    
    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private commentApiService: CommentApiService,
        private postApiService: PostApiService,
        private userService: UserService,
        private sessionService: SessionService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.postId = this.route.snapshot.paramMap.get('id')!;
    }

    public ngOnInit(): void {

        this.userService
            .getById(this.sessionService.sessionInformation!.id.toString())
            .subscribe((user: User) => { 
                this.user = user;
                this.userName = user.userName;
            });

        this.postApiService
            .detail(this.postId)
            .subscribe((post:Post) => {
                this.post = post;
                this.postTitle = post.title;
            });

        this.initForm();
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

    private initForm(comment?: Comment): void {

        this.commentForm = this.fb.group({
            content: [
                comment ? comment.content : '',
                [
                Validators.required,
                Validators.max(2000)
                ]
            ],
        });
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate([`articles/detail/${this.postId}`]);
    }
}
