
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { Topic } from '../../interfaces/topic.interface';
import { TopicApiService } from '../../services/topic-api.service';

@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss']
})
export class ListComponent {
  
    public topics$: Observable<Topic[]> = this.topicApiService.all();  
    public isSubscribe = false;  
    public userId: number;

    constructor(
        private sessionService: SessionService,
        private topicApiService: TopicApiService
    ) {    
        this.userId = this.sessionService.sessionInformation!.id;
    }

    public subscribe(id:number): void {
        this.topicApiService.subscribe(id.toString(), this.userId.toString()).subscribe(_ => this.fetchTopic(id));
    }

    public unSubscribe(id:number): void {
        this.topicApiService.unSubscribe(id.toString(), this.userId.toString()).subscribe(_ => this.fetchTopic(id));
    }

    private fetchTopic(id:number): void {

        this.topicApiService
            .detail(id.toString())
            .subscribe((topic: Topic) => {
                this.isSubscribe = topic.users.some(u => u === this.sessionService.sessionInformation!.id);
            });
    }
}
