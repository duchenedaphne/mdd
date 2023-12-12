
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';

@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss']
})
export class ListComponent {

    public posts$: Observable<Post[]> = this.postApiService.all();

    constructor(
        private postApiService: PostApiService
    ) { }
}
