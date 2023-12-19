
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';
import { SessionService } from 'src/app/services/session.service';
import { TopicApiService } from 'src/app/features/topics/services/topic-api.service';
import { Topic } from 'src/app/features/topics/interfaces/topic.interface';

@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

    public topics$: Observable<Topic[]>;
    public posts$: Observable<Post[]> | undefined; 
    public posts: Post[] = []; 
    public userId;
    public isOrder = false;

    constructor(
        private sessionService: SessionService,
        private topicApiService: TopicApiService,
        private postApiService: PostApiService
    ) {
        this.userId = this.sessionService.sessionInformation!.id;
        this.topics$ = this.topicApiService.allByUserId(this.userId.toString());
    }

    ngOnInit(): void {
        this.topics$.subscribe(
            (topics: Topic[]) => {
                topics.forEach((topic: Topic) => {

                    if (topic.id != undefined) {
                        this.posts$ = this.postApiService.allByTopicId(topic.id?.toString());

                        this.posts$.subscribe(
                            (posts: Post[]) =>
                                posts.forEach(
                                    (post: Post) => this.posts?.push(post)
                                )                         
                        )
                    }
                });
            }
        )
    }

    public order(): void {
        this.isOrder = !this.isOrder;
    }
}
