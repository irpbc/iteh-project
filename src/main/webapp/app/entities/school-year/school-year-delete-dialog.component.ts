import { Component } from '@angular/core';
import { SchoolYearService } from './school-year.service';

@Component({
    selector: 'jhi-school-year-delete-dialog',
    template: `<delete-dialog [entityName]="'schoolYear'" [service]="schoolYearService"></delete-dialog>`
})
export class SchoolYearDeleteDialogComponent {

    constructor(public schoolYearService: SchoolYearService) {
    }
}
