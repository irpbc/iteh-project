import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';

import { Semester } from './semester.model';
import { SemesterService } from './semester.service';
import { SchoolYear, SchoolYearService } from '../school-year';
import { FieldDef, FieldType, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-semester-dialog',
    template: `
        <entity-edit-dialog [entityName]="'semester'" [fieldDefs]="fields"
                            [entityService]="semesterService"
                            [entityConstructor]="entityConstructor"></entity-edit-dialog>`
})
export class SemesterDialogComponent implements OnInit {

    entityConstructor = Semester;

    schoolyears: SchoolYear[];

    fields: FieldDef[];

    constructor(private jhiAlertService: JhiAlertService,
                public semesterService: SemesterService,
                private schoolYearService: SchoolYearService) {
    }

    ngOnInit() {
        this.fields = [
            {
                name: 'name',
                type: FieldType.STRING,
                required: true,
                minLength: 5,
                maxLength: 100
            },
            {
                name: 'year',
                type: FieldType.REL_TO_ONE,
                required: true,
                optionNameField: 'name'
            }
        ];

        this.schoolYearService.query().subscribe(
            (res: ResponseWrapper) => {
                this.schoolyears = res.json;
                this.fields[1].options = this.schoolyears;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
