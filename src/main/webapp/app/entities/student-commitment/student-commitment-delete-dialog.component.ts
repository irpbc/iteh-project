import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StudentCommitment } from './student-commitment.model';
import { StudentCommitmentPopupService } from './student-commitment-popup.service';
import { StudentCommitmentService } from './student-commitment.service';

@Component({
    selector: 'jhi-student-commitment-delete-dialog',
    templateUrl: './student-commitment-delete-dialog.component.html'
})
export class StudentCommitmentDeleteDialogComponent {

    studentCommitment: StudentCommitment;

    constructor(
        private studentCommitmentService: StudentCommitmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentCommitmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'studentCommitmentListModification',
                content: 'Deleted an studentCommitment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-student-commitment-delete-popup',
    template: ''
})
export class StudentCommitmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentCommitmentPopupService: StudentCommitmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.studentCommitmentPopupService
                .open(StudentCommitmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
