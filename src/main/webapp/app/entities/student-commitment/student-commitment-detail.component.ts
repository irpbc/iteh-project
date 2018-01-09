import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { StudentCommitment } from './student-commitment.model';
import { StudentCommitmentService } from './student-commitment.service';

@Component({
    selector: 'jhi-student-commitment-detail',
    templateUrl: './student-commitment-detail.component.html'
})
export class StudentCommitmentDetailComponent implements OnInit, OnDestroy {

    studentCommitment: StudentCommitment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private studentCommitmentService: StudentCommitmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStudentCommitments();
    }

    load(id) {
        this.studentCommitmentService.find(id).subscribe((studentCommitment) => {
            this.studentCommitment = studentCommitment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStudentCommitments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'studentCommitmentListModification',
            (response) => this.load(this.studentCommitment.id)
        );
    }
}
