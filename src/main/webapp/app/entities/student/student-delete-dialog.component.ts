import { Component } from '@angular/core';

import { UserService } from '../../shared';

@Component({
    selector: 'jhi-student-delete-dialog',
    template: `<delete-dialog [entityName]="'student'" [service]="userService"></delete-dialog>`
})
export class StudentDeleteDialogComponent {

    constructor(public userService: UserService) {
    }
}
