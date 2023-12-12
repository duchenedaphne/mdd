
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { CommentApiService } from '../../services/comment-api.service';
import { Comment } from '../../../posts/interfaces/comment.interface';

@Component({
    selector: 'app-comments-form',
    templateUrl: './comments-form.component.html',
    styleUrls: ['./comments-form.component.scss']
})
export class CommentsFormComponent implements OnInit {

    public commentForm: FormGroup | undefined;    
    public userId: number;
    public postId: string;

    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private commentApiService: CommentApiService,
        private sessionService: SessionService,
        private route: ActivatedRoute,
        private router: Router
    ) {
        this.userId = this.sessionService.sessionInformation!.id;
        this.postId = this.route.snapshot.paramMap.get('id')!;
    }

    public ngOnInit(): void {
        this.initForm();
    }

    public submit(): void {

        const comment = this.commentForm?.value as Comment;
        comment.post_id = parseInt(this.postId);
        comment.user_id = this.userId;

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
