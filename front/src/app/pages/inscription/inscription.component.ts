import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent {

  public onError = false;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    userName: [
      '',
      [
        Validators.required,
        Validators.min(3),
        Validators.max(20)
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3),
        Validators.max(40)
      ]
    ]
  });

  // constructor(private authService: AuthService,
  //             private fb: FormBuilder,
  //             private router: Router) {
  // }

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) { }

  public submit(): void {
  //   const registerRequest = this.form.value as RegisterRequest;
  //   this.authService.register(registerRequest).subscribe({
  //       next: (_: void) => this.router.navigate(['/login']),
  //       error: _ => this.onError = true,
  //     }
  //   );
  }
}
