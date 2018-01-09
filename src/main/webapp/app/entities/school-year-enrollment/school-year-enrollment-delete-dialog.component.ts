import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SchoolYearEnrollment } from './school-year-enrollment.model';
import { SchoolYearEnrollmentPopupService } from './school-year-enrollment-popup.service';
import { SchoolYearEnrollmentService } from './school-year-enrollment.service';

@Component({
    selector: 'jhi-school-year-enrollment-delete-dialog',
    templateUrl: './school-year-enrollment-delete-dialog.component.html'
})
export class SchoolYearEnrollmentDeleteDialogComponent {

    schoolYearEnrollment: SchoolYearEnrollment;

    constructor(
        private schoolYearEnrollmentService: SchoolYearEnrollmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.schoolYearEnrollmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'schoolYearEnrollmentListModification',
                content: 'Deleted an schoolYearEnrollment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-school-year-enrollment-delete-popup',
    template: ''
})
export class SchoolYearEnrollmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schoolYearEnrollmentPopupService: SchoolYearEnrollmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.schoolYearEnrollmentPopupService
                .open(SchoolYearEnrollmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
