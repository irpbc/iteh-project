import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CourseEnrollment } from './course-enrollment.model';
import { CourseEnrollmentPopupService } from './course-enrollment-popup.service';
import { CourseEnrollmentService } from './course-enrollment.service';
import { SchoolYearEnrollment, SchoolYearEnrollmentService } from '../school-year-enrollment';
import { Course, CourseService } from '../course';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-course-enrollment-dialog',
    templateUrl: './course-enrollment-dialog.component.html'
})
export class CourseEnrollmentDialogComponent implements OnInit {

    courseEnrollment: CourseEnrollment;
    isSaving: boolean;

    schoolyearenrollments: SchoolYearEnrollment[];

    courses: Course[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private courseEnrollmentService: CourseEnrollmentService,
        private schoolYearEnrollmentService: SchoolYearEnrollmentService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.schoolYearEnrollmentService.query()
            .subscribe((res: ResponseWrapper) => { this.schoolyearenrollments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.courseService.query()
            .subscribe((res: ResponseWrapper) => { this.courses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.courseEnrollment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.courseEnrollmentService.update(this.courseEnrollment));
        } else {
            this.subscribeToSaveResponse(
                this.courseEnrollmentService.create(this.courseEnrollment));
        }
    }

    private subscribeToSaveResponse(result: Observable<CourseEnrollment>) {
        result.subscribe((res: CourseEnrollment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CourseEnrollment) {
        this.eventManager.broadcast({ name: 'courseEnrollmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSchoolYearEnrollmentById(index: number, item: SchoolYearEnrollment) {
        return item.id;
    }

    trackCourseById(index: number, item: Course) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-course-enrollment-popup',
    template: ''
})
export class CourseEnrollmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private courseEnrollmentPopupService: CourseEnrollmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.courseEnrollmentPopupService
                    .open(CourseEnrollmentDialogComponent as Component, params['id']);
            } else {
                this.courseEnrollmentPopupService
                    .open(CourseEnrollmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
