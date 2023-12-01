import { Component } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent {

  constructor(
    private router: Router
  ) {}

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }

  register() {
    this.router.navigateByUrl('register');
  }
}
