
import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../../interfaces/post.interface';
import { PostApiService } from '../../services/post-api.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/interfaces/user.interface';

@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.scss']
})
export class ListComponent {

    public posts$: Observable<Post[]> = this.postApiService.all();
    public author: string | undefined;

    constructor(
        private postApiService: PostApiService,
        private userService: UserService
    ) { }

    public fetchAuthor(id:number): void {

        this.userService
            .getById(id.toString())
            .subscribe((user: User) => {
                this.author = user.userName;
            });
    }
}
