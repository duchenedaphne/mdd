
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentApiService {

  public pathService = 'api/comment';

  constructor(private httpClient: HttpClient) {
  }

  public allByPostId(id: string): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/post/${id}`);
  }

  public create(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(this.pathService, comment);
  }

  public detail(id: string): Observable<Comment> {
    return this.httpClient.get<Comment>(`${this.pathService}/${id}`);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }
}
