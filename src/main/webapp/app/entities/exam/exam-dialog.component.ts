import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Exam } from './exam.model';
import { ExamPopupService } from './exam-popup.service';
import { ExamService } from './exam.service';
import { ExamPeriod, ExamPeriodService } from '../exam-period';
import { Course, CourseService } from '../course';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-exam-dialog',
    templateUrl: './exam-dialog.component.html'
})
export class ExamDialogComponent implements OnInit {

    exam: Exam;
    isSaving: boolean;

    examperiods: ExamPeriod[];

    courses: Course[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private examService: ExamService,
        private examPeriodService: ExamPeriodService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.examPeriodService.query()
            .subscribe((res: ResponseWrapper) => { this.examperiods = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.courseService.query()
            .subscribe((res: ResponseWrapper) => { this.courses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.exam.id !== undefined) {
            this.subscribeToSaveResponse(
                this.examService.update(this.exam));
        } else {
            this.subscribeToSaveResponse(
                this.examService.create(this.exam));
        }
    }

    private subscribeToSaveResponse(result: Observable<Exam>) {
        result.subscribe((res: Exam) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Exam) {
        this.eventManager.broadcast({ name: 'examListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackExamPeriodById(index: number, item: ExamPeriod) {
        return item.id;
    }

    trackCourseById(index: number, item: Course) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-exam-popup',
    template: ''
})
export class ExamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private examPopupService: ExamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.examPopupService
                    .open(ExamDialogComponent as Component, params['id']);
            } else {
                this.examPopupService
                    .open(ExamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
