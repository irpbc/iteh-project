import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { StudentExam } from './student-exam.model';
import { StudentExamService } from './student-exam.service';

@Component({
    selector: 'jhi-student-exam-detail',
    templateUrl: './student-exam-detail.component.html'
})
export class StudentExamDetailComponent implements OnInit, OnDestroy {

    studentExam: StudentExam;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private studentExamService: StudentExamService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStudentExams();
    }

    load(id) {
        this.studentExamService.find(id).subscribe((studentExam) => {
            this.studentExam = studentExam;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStudentExams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'studentExamListModification',
            (response) => this.load(this.studentExam.id)
        );
    }
}
