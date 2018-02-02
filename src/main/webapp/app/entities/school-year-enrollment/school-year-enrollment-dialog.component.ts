import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { SchoolYearEnrollment } from './school-year-enrollment.model';
import { SchoolYearEnrollmentPopupService } from './school-year-enrollment-popup.service';
import { SchoolYearEnrollmentService } from './school-year-enrollment.service';
import { ResponseWrapper, User, UserService, UserType } from '../../shared';
import { SchoolYear, SchoolYearService } from '../school-year';

@Component({
    selector: 'jhi-school-year-enrollment-dialog',
    templateUrl: './school-year-enrollment-dialog.component.html'
})
export class SchoolYearEnrollmentDialogComponent implements OnInit {

    schoolYearEnrollment: SchoolYearEnrollment;
    isSaving: boolean;

    users: User[];

    schoolyears: SchoolYear[];

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private schoolYearEnrollmentService: SchoolYearEnrollmentService,
                private userService: UserService,
                private schoolYearService: SchoolYearService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query({ filter: { 'userType.equals': UserType.ST } }).subscribe(
            (res: ResponseWrapper) => this.users = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.schoolYearService.query().subscribe(
            (res: ResponseWrapper) => this.schoolyears = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schoolYearEnrollment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schoolYearEnrollmentService.update(this.schoolYearEnrollment));
        } else {
            this.subscribeToSaveResponse(
                this.schoolYearEnrollmentService.create(this.schoolYearEnrollment));
        }
    }

    private subscribeToSaveResponse(result: Observable<SchoolYearEnrollment>) {
        result.subscribe((res: SchoolYearEnrollment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SchoolYearEnrollment) {
        this.eventManager.broadcast({ name: 'schoolYearEnrollmentListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackSchoolYearById(index: number, item: SchoolYear) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-school-year-enrollment-popup',
    template: ''
})
export class SchoolYearEnrollmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private schoolYearEnrollmentPopupService: SchoolYearEnrollmentPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.schoolYearEnrollmentPopupService
                    .open(SchoolYearEnrollmentDialogComponent as Component, params['id']);
            } else {
                this.schoolYearEnrollmentPopupService
                    .open(SchoolYearEnrollmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
