import { Component } from '@angular/core';
import { SemesterService } from './semester.service';

@Component({
    selector: 'jhi-semester-delete-dialog',
    template: `<delete-dialog [entityName]="'semester'" [service]="semesterService"></delete-dialog>`
})
export class SemesterDeleteDialogComponent {

    constructor(public semesterService: SemesterService) {
    }
}
