import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ExamPeriod } from './exam-period.model';
import { ExamPeriodService } from './exam-period.service';

@Component({
    selector: 'jhi-exam-period-detail',
    templateUrl: './exam-period-detail.component.html'
})
export class ExamPeriodDetailComponent implements OnInit, OnDestroy {

    examPeriod: ExamPeriod;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private examPeriodService: ExamPeriodService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExamPeriods();
    }

    load(id) {
        this.examPeriodService.find(id).subscribe((examPeriod) => {
            this.examPeriod = examPeriod;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExamPeriods() {
        this.eventSubscriber = this.eventManager.subscribe(
            'examPeriodListModification',
            (response) => this.load(this.examPeriod.id)
        );
    }
}
