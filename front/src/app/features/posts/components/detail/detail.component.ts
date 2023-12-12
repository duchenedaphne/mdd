
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';
import { SessionService } from 'src/app/services/session.service';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';
import { TopicApiService } from 'src/app/features/topics/services/topic-api.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
    selector: 'app-detail',
    templateUrl: './detail.component.html',
    styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

    public post: Post | undefined;
    public topic: string | undefined;
    public author: string | undefined;
    public title: string | undefined;
    public content: string | undefined;
    public createdAt: Date | undefined;
    public updatedAt: Date | undefined;

    public postId: string;
    public userId: string;
    public isAuthor = false;

    constructor(
        private route: ActivatedRoute,
        private sessionService: SessionService,
        private postApiService: PostApiService,
        private topicApiService: TopicApiService,
        private userService: UserService,
        private matSnackBar: MatSnackBar,
        private router: Router
    ) {
        this.postId = this.route.snapshot.paramMap.get('id')!;
        this.userId = this.sessionService.sessionInformation!.id.toString();
    }

    public ngOnInit(): void {
        
        this.postApiService
            .detail(this.postId)
            .subscribe((post: Post) => {
                this.post = post;
                this.title = post.title;
                this.content = post.content;
                this.createdAt = post.createdAt;
                this.updatedAt = post.updatedAt;

                if (this.userId == post.user_id.toString())
                this.isAuthor = true;

                this.userService
                    .getById(post.user_id.toString())
                    .subscribe((user: User) => this.author = user.userName);

                this.topicApiService
                    .detail(post.topic_id.toString())
                    .subscribe((topic: Topic) => this.topic = topic.name);
            });
    }

    public delete(): void {
        
        this.postApiService
            .delete(this.postId)
            .subscribe((_: any) => {
                this.matSnackBar.open('Article supprimé !', 'Close', { duration: 3000 });
                this.router.navigate(['/articles']);
                }
            );
    }
}
