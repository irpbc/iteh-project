import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CourseEnrollment } from './course-enrollment.model';
import { CourseEnrollmentPopupService } from './course-enrollment-popup.service';
import { CourseEnrollmentService } from './course-enrollment.service';

@Component({
    selector: 'jhi-course-enrollment-delete-dialog',
    templateUrl: './course-enrollment-delete-dialog.component.html'
})
export class CourseEnrollmentDeleteDialogComponent {

    courseEnrollment: CourseEnrollment;

    constructor(
        private courseEnrollmentService: CourseEnrollmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courseEnrollmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'courseEnrollmentListModification',
                content: 'Deleted an courseEnrollment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-course-enrollment-delete-popup',
    template: ''
})
export class CourseEnrollmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private courseEnrollmentPopupService: CourseEnrollmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.courseEnrollmentPopupService
                .open(CourseEnrollmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
