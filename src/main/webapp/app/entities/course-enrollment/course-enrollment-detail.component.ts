import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CourseEnrollment } from './course-enrollment.model';
import { CourseEnrollmentService } from './course-enrollment.service';

@Component({
    selector: 'jhi-course-enrollment-detail',
    templateUrl: './course-enrollment-detail.component.html'
})
export class CourseEnrollmentDetailComponent implements OnInit, OnDestroy {

    courseEnrollment: CourseEnrollment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private courseEnrollmentService: CourseEnrollmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCourseEnrollments();
    }

    load(id) {
        this.courseEnrollmentService.find(id).subscribe((courseEnrollment) => {
            this.courseEnrollment = courseEnrollment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCourseEnrollments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'courseEnrollmentListModification',
            (response) => this.load(this.courseEnrollment.id)
        );
    }
}
