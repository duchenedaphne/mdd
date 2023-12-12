
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TopicApiService } from '../../services/topic-api.service';
import { Topic } from '../../interfaces/topic.interface';

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
        private router: Router
    ) { }

    public ngOnInit(): void {
        this.initForm();
    }

    public submit(): void {

        const topic = this.topicForm?.value as Topic;

        this.topicApiService
        .create(topic)
        .subscribe((_: Topic) => this.exitPage('Thème créé !'));
    }

    private initForm(topic?: Topic): void {

        this.topicForm = this.fb.group({
            name: [
                topic ? topic.name : '',
                [Validators.required]
            ],
            description: [
                topic ? topic.description : '',
                [
                    Validators.required,
                    Validators.max(2000)
                ]
            ],
        });
    }

    private exitPage(message: string): void {
        this.matSnackBar.open(message, 'Close', { duration: 3000 });
        this.router.navigate(['themes']);
    }
}
