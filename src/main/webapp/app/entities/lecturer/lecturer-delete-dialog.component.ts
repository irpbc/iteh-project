import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Lecturer } from './lecturer.model';
import { LecturerPopupService } from './lecturer-popup.service';
import { LecturerService } from './lecturer.service';

@Component({
    selector: 'jhi-lecturer-delete-dialog',
    templateUrl: './lecturer-delete-dialog.component.html'
})
export class LecturerDeleteDialogComponent {

    lecturer: Lecturer;

    constructor(
        private lecturerService: LecturerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lecturerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lecturerListModification',
                content: 'Deleted an lecturer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lecturer-delete-popup',
    template: ''
})
export class LecturerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lecturerPopupService: LecturerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lecturerPopupService
                .open(LecturerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
