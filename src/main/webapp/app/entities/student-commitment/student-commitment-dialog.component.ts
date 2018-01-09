import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StudentCommitment } from './student-commitment.model';
import { StudentCommitmentPopupService } from './student-commitment-popup.service';
import { StudentCommitmentService } from './student-commitment.service';
import { CourseEnrollment, CourseEnrollmentService } from '../course-enrollment';
import { Commitment, CommitmentService } from '../commitment';
import { Lecturer, LecturerService } from '../lecturer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-student-commitment-dialog',
    templateUrl: './student-commitment-dialog.component.html'
})
export class StudentCommitmentDialogComponent implements OnInit {

    studentCommitment: StudentCommitment;
    isSaving: boolean;

    courseenrollments: CourseEnrollment[];

    commitments: Commitment[];

    lecturers: Lecturer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private studentCommitmentService: StudentCommitmentService,
        private courseEnrollmentService: CourseEnrollmentService,
        private commitmentService: CommitmentService,
        private lecturerService: LecturerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.courseEnrollmentService.query()
            .subscribe((res: ResponseWrapper) => { this.courseenrollments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.commitmentService.query()
            .subscribe((res: ResponseWrapper) => { this.commitments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lecturerService.query()
            .subscribe((res: ResponseWrapper) => { this.lecturers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.studentCommitment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.studentCommitmentService.update(this.studentCommitment));
        } else {
            this.subscribeToSaveResponse(
                this.studentCommitmentService.create(this.studentCommitment));
        }
    }

    private subscribeToSaveResponse(result: Observable<StudentCommitment>) {
        result.subscribe((res: StudentCommitment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: StudentCommitment) {
        this.eventManager.broadcast({ name: 'studentCommitmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCourseEnrollmentById(index: number, item: CourseEnrollment) {
        return item.id;
    }

    trackCommitmentById(index: number, item: Commitment) {
        return item.id;
    }

    trackLecturerById(index: number, item: Lecturer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-student-commitment-popup',
    template: ''
})
export class StudentCommitmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentCommitmentPopupService: StudentCommitmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.studentCommitmentPopupService
                    .open(StudentCommitmentDialogComponent as Component, params['id']);
            } else {
                this.studentCommitmentPopupService
                    .open(StudentCommitmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
