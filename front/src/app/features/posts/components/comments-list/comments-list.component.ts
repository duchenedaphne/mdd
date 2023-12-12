
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CommentApiService } from 'src/app/features/posts/services/comment-api.service';
import { Comment } from '../../interfaces/comment.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-comments-list',
    templateUrl: './comments-list.component.html',
    styleUrls: ['./comments-list.component.scss']
})
export class CommentsListComponent implements OnInit {

    public comments$: Observable<Comment[]>;

    public comment: Comment | undefined;
    public author: string | undefined;
    
    public userId: number;
    public postId: string;

    constructor(
        private sessionService: SessionService,
        private userService: UserService,
        private commentApiService: CommentApiService,
        private route: ActivatedRoute,
        private matSnackBar: MatSnackBar,
        private router: Router
    ) {        
        this.userId = this.sessionService.sessionInformation!.id;
        this.postId = this.route.snapshot.paramMap.get('id')!;
        this.comments$  = this.commentApiService.allByPostId(this.postId);
    }

    public ngOnInit(): void {
    }

    public fetchAuthor(id:number): void {

        this.userService
            .getById(id.toString())
            .subscribe((user: User) => this.author = user.userName);
    }

    public delete(id:number): void {

        this.commentApiService
            .delete(id.toString())
            .subscribe((_: any) => {
                this.matSnackBar.open('Commentaire supprimé !', 'Close', { duration: 3000 });
                this.router.navigate([`articles/${this.postId}`]);
                }
            );
    }
}
