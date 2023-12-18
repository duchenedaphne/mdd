
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { CommentApiService } from 'src/app/features/posts/services/comment-api.service';
import { Comment } from '../../interfaces/comment.interface';

@Component({
    selector: 'app-comments-list',
    templateUrl: './comments-list.component.html',
    styleUrls: ['./comments-list.component.scss']
})
export class CommentsListComponent implements OnInit {

    public comments$: Observable<Comment[]>;
    public postId: string;

    constructor(
        private commentApiService: CommentApiService,
        private route: ActivatedRoute
    ) {  
        this.postId = this.route.snapshot.paramMap.get('id')!;
        this.comments$  = this.commentApiService.allByPostId(this.postId);
    }

    public ngOnInit(): void {
    }
}
