import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SchoolYearEnrollment } from './school-year-enrollment.model';
import { SchoolYearEnrollmentService } from './school-year-enrollment.service';

@Component({
    selector: 'jhi-school-year-enrollment-detail',
    templateUrl: './school-year-enrollment-detail.component.html'
})
export class SchoolYearEnrollmentDetailComponent implements OnInit, OnDestroy {

    schoolYearEnrollment: SchoolYearEnrollment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private schoolYearEnrollmentService: SchoolYearEnrollmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSchoolYearEnrollments();
    }

    load(id) {
        this.schoolYearEnrollmentService.find(id).subscribe((schoolYearEnrollment) => {
            this.schoolYearEnrollment = schoolYearEnrollment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSchoolYearEnrollments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'schoolYearEnrollmentListModification',
            (response) => this.load(this.schoolYearEnrollment.id)
        );
    }
}
