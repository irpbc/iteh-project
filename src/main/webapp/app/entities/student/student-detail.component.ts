import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-student-detail',
    templateUrl: './student-detail.component.html'
})
export class StudentDetailComponent implements OnInit, OnDestroy {

    student: User;
    private subscription: Subscription;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(login) {
        this.userService.find(login).subscribe((user) => {
            this.student = user;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
