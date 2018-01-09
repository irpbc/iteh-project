import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Semester } from './semester.model';
import { SemesterPopupService } from './semester-popup.service';
import { SemesterService } from './semester.service';
import { SchoolYear, SchoolYearService } from '../school-year';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-semester-dialog',
    templateUrl: './semester-dialog.component.html'
})
export class SemesterDialogComponent implements OnInit {

    semester: Semester;
    isSaving: boolean;

    schoolyears: SchoolYear[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private semesterService: SemesterService,
        private schoolYearService: SchoolYearService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.schoolYearService.query()
            .subscribe((res: ResponseWrapper) => { this.schoolyears = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.semester.id !== undefined) {
            this.subscribeToSaveResponse(
                this.semesterService.update(this.semester));
        } else {
            this.subscribeToSaveResponse(
                this.semesterService.create(this.semester));
        }
    }

    private subscribeToSaveResponse(result: Observable<Semester>) {
        result.subscribe((res: Semester) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Semester) {
        this.eventManager.broadcast({ name: 'semesterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSchoolYearById(index: number, item: SchoolYear) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-semester-popup',
    template: ''
})
export class SemesterPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private semesterPopupService: SemesterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.semesterPopupService
                    .open(SemesterDialogComponent as Component, params['id']);
            } else {
                this.semesterPopupService
                    .open(SemesterDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
