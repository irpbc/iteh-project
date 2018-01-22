import { Component, OnInit } from '@angular/core';

import { SchoolYear } from './school-year.model';
import { SchoolYearService } from './school-year.service';
import { FieldDef, FieldType } from '../../shared';

@Component({
    selector: 'jhi-school-year-dialog',
    template: `
        <entity-edit-dialog [entityName]="'schoolYear'" [fieldDefs]="fieldDefs"
                            [entityService]="schoolYearService"
                            [entityConstructor]="entityConstructor"></entity-edit-dialog>`
})
export class SchoolYearDialogComponent implements OnInit {

    entityConstructor = SchoolYear;
    fieldDefs: FieldDef[];

    constructor(public schoolYearService: SchoolYearService) {
    }

    ngOnInit() {
        this.fieldDefs = [
            {
                name     : 'name',
                type     : FieldType.STRING,
                required : true,
                minLength: 5,
                maxLength: 100
            },
            {
                name    : 'startDate',
                type    : FieldType.DATE,
                required: true
            },
            {
                name    : 'endDate',
                type    : FieldType.DATE,
                required: true
            }
        ];
    }
}
