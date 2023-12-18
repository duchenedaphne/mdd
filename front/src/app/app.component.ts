
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './features/auth/services/auth.service';
import { SessionInformation } from './interfaces/sessionInformation.interface';
import { SessionService } from './services/session.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    public $isMenu = false;

    constructor(
        private authService: AuthService,
        private router: Router,
        private sessionService: SessionService
    ) { }

    public $isLogged(): Observable<boolean> {
        return this.sessionService.$isLogged();
    }

    public open(): void {
        this.$isMenu = true;
    }

    public close(): void {
        this.$isMenu = false;
    }

    public articles(): void {
        this.router.navigate(['articles']);
        this.$isMenu = false;
    }

    public themes(): void {
        this.router.navigate(['themes']);
        this.$isMenu = false;
    }

    public me(): void {
        this.router.navigate(['me']);
        this.$isMenu = false;
    }
}
