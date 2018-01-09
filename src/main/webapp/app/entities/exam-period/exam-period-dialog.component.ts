import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExamPeriod } from './exam-period.model';
import { ExamPeriodPopupService } from './exam-period-popup.service';
import { ExamPeriodService } from './exam-period.service';
import { SchoolYear, SchoolYearService } from '../school-year';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-exam-period-dialog',
    templateUrl: './exam-period-dialog.component.html'
})
export class ExamPeriodDialogComponent implements OnInit {

    examPeriod: ExamPeriod;
    isSaving: boolean;

    schoolyears: SchoolYear[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private examPeriodService: ExamPeriodService,
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
        if (this.examPeriod.id !== undefined) {
            this.subscribeToSaveResponse(
                this.examPeriodService.update(this.examPeriod));
        } else {
            this.subscribeToSaveResponse(
                this.examPeriodService.create(this.examPeriod));
        }
    }

    private subscribeToSaveResponse(result: Observable<ExamPeriod>) {
        result.subscribe((res: ExamPeriod) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ExamPeriod) {
        this.eventManager.broadcast({ name: 'examPeriodListModification', content: 'OK'});
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
    selector: 'jhi-exam-period-popup',
    template: ''
})
export class ExamPeriodPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private examPeriodPopupService: ExamPeriodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.examPeriodPopupService
                    .open(ExamPeriodDialogComponent as Component, params['id']);
            } else {
                this.examPeriodPopupService
                    .open(ExamPeriodDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
