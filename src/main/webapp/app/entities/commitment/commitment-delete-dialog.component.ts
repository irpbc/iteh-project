import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Commitment } from './commitment.model';
import { CommitmentPopupService } from './commitment-popup.service';
import { CommitmentService } from './commitment.service';

@Component({
    selector: 'jhi-commitment-delete-dialog',
    templateUrl: './commitment-delete-dialog.component.html'
})
export class CommitmentDeleteDialogComponent {

    commitment: Commitment;

    constructor(
        private commitmentService: CommitmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commitmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'commitmentListModification',
                content: 'Deleted an commitment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commitment-delete-popup',
    template: ''
})
export class CommitmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commitmentPopupService: CommitmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.commitmentPopupService
                .open(CommitmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
