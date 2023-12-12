
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicApiService {

  public pathService = 'api/topic';

  constructor(private httpClient: HttpClient) {
  }

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public allByUserId(id: string): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/user/${id}`);
  }

  public create(topic: Topic): Observable<Topic> {
    return this.httpClient.post<Topic>(this.pathService, topic);
  }

  public detail(id: string): Observable<Topic> {
    return this.httpClient.get<Topic>(`${this.pathService}/${id}`);
  }

  public delete(id: string): Observable<any> {
    return this.httpClient.delete(`${this.pathService}/${id}`);
  }

  public subscribe(id: string, userId: string): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/${id}/subscribe/${userId}`, null);
  }

  public unSubscribe(id: string, userId: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/${id}/subscribe/${userId}`);
  }
}
