
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TopicApiService } from '../../services/topic-api.service';
import { Topic } from '../../interfaces/topic.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';

@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  
    public topicForm: FormGroup | undefined;

    constructor(
        private fb: FormBuilder,
        private matSnackBar: MatSnackBar,
        private topicApiService: TopicApiService,
        private sessionService: SessionService,
        private router: Router
    ) { }

    get user(): SessionInformation | undefined {
      return this.sessionService.sessionInformation;
    }

    public ngOnInit(): void {
        this.initForm();
    }

    private initForm(): void {

        this.topicForm = this.fb.group({
            name: [
                '',
                [Validators.required]
            ],
            description: [
                '',
                [
                    Validators.required,
                    Validators.max(2000)
                ]
            ],
        });
    }

    public submit(): void {

        const topic = this.topicForm?.value as Topic;

        this.topicApiService
            .create(topic)
            .subscribe((_: Topic) => this.exitPage('Thème créé !'));
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate(['themes']);
    }
}
