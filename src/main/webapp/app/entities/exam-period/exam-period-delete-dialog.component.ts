import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExamPeriod } from './exam-period.model';
import { ExamPeriodPopupService } from './exam-period-popup.service';
import { ExamPeriodService } from './exam-period.service';

@Component({
    selector: 'jhi-exam-period-delete-dialog',
    templateUrl: './exam-period-delete-dialog.component.html'
})
export class ExamPeriodDeleteDialogComponent {

    examPeriod: ExamPeriod;

    constructor(
        private examPeriodService: ExamPeriodService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examPeriodService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'examPeriodListModification',
                content: 'Deleted an examPeriod'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exam-period-delete-popup',
    template: ''
})
export class ExamPeriodDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private examPeriodPopupService: ExamPeriodPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.examPeriodPopupService
                .open(ExamPeriodDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
