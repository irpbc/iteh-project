import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { StudentExam } from './student-exam.model';
import { StudentExamPopupService } from './student-exam-popup.service';
import { StudentExamService } from './student-exam.service';
import { Student, StudentService } from '../student';
import { Exam, ExamService } from '../exam';
import { Lecturer, LecturerService } from '../lecturer';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-student-exam-dialog',
    templateUrl: './student-exam-dialog.component.html'
})
export class StudentExamDialogComponent implements OnInit {

    studentExam: StudentExam;
    isSaving: boolean;

    students: Student[];

    exams: Exam[];

    lecturers: Lecturer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private studentExamService: StudentExamService,
        private studentService: StudentService,
        private examService: ExamService,
        private lecturerService: LecturerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.studentService.query()
            .subscribe((res: ResponseWrapper) => { this.students = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.examService.query()
            .subscribe((res: ResponseWrapper) => { this.exams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lecturerService.query()
            .subscribe((res: ResponseWrapper) => { this.lecturers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.eventManager.broadcast({ name: 'studentExamListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStudentById(index: number, item: Student) {
        return item.id;
    }

    trackExamById(index: number, item: Exam) {
        return item.id;
    }

    trackLecturerById(index: number, item: Lecturer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-student-exam-popup',
    template: ''
})
export class StudentExamPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentExamPopupService: StudentExamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
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
