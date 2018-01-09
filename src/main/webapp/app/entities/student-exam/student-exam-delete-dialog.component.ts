import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StudentExam } from './student-exam.model';
import { StudentExamPopupService } from './student-exam-popup.service';
import { StudentExamService } from './student-exam.service';

@Component({
    selector: 'jhi-student-exam-delete-dialog',
    templateUrl: './student-exam-delete-dialog.component.html'
})
export class StudentExamDeleteDialogComponent {

    studentExam: StudentExam;

    constructor(
        private studentExamService: StudentExamService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentExamService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'studentExamListModification',
                content: 'Deleted an studentExam'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-exam-delete-popup',
    template: ''
})
export class StudentExamDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentExamPopupService: StudentExamPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.studentExamPopupService
                .open(StudentExamDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
