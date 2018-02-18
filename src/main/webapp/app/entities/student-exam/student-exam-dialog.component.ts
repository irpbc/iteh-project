import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { StudentExam } from './student-exam.model';
import { StudentExamPopupService } from './student-exam-popup.service';
import { StudentExamService } from './student-exam.service';
import { Principal, ResponseWrapper, User, UserService, UserType } from '../../shared';
import { Exam, ExamService } from '../exam';

@Component({
    selector: 'jhi-student-exam-dialog',
    templateUrl: './student-exam-dialog.component.html'
})
export class StudentExamDialogComponent implements OnInit {

    studentExam: StudentExam;
    isSaving: boolean;

    lecturers: User[];

    exams: Exam[];

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private studentExamService: StudentExamService,
                private userService: UserService,
                private examService: ExamService,
                private eventManager: JhiEventManager,
                private principal: Principal) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.examService.query().subscribe(
            (res: ResponseWrapper) => this.exams = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    set object(studentExam: StudentExam) {
        this.studentExam = studentExam;
        this.principal.identity().then((lect) => {
            this.studentExam.evaluatedById = lect.id;
            this.studentExam.evaluatedByFullName = lect.fullName;
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.studentExam.id !== undefined) {
            this.subscribeToSaveResponse(
                this.studentExamService.update(this.studentExam));
        } else {
            this.subscribeToSaveResponse(
                this.studentExamService.create(this.studentExam));
        }
    }

    private subscribeToSaveResponse(result: Observable<StudentExam>) {
        result.subscribe((res: StudentExam) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: StudentExam) {
        this.eventManager.broadcast({ name: 'studentExamListModification', content: 'OK' });
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

    trackExamById(index: number, item: Exam) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-student-exam-popup',
    template: ''
})
export class StudentExamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private studentExamPopupService: StudentExamPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.studentExamPopupService
                    .open(StudentExamDialogComponent as Component, params['id']);
            } else {
                this.studentExamPopupService
                    .open(StudentExamDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
