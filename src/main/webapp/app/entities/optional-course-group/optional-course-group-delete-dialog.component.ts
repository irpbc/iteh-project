import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OptionalCourseGroup } from './optional-course-group.model';
import { OptionalCourseGroupPopupService } from './optional-course-group-popup.service';
import { OptionalCourseGroupService } from './optional-course-group.service';

@Component({
    selector: 'jhi-optional-course-group-delete-dialog',
    templateUrl: './optional-course-group-delete-dialog.component.html'
})
export class OptionalCourseGroupDeleteDialogComponent {

    optionalCourseGroup: OptionalCourseGroup;

    constructor(
        private optionalCourseGroupService: OptionalCourseGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.optionalCourseGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'optionalCourseGroupListModification',
                content: 'Deleted an optionalCourseGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-optional-course-group-delete-popup',
    template: ''
})
export class OptionalCourseGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private optionalCourseGroupPopupService: OptionalCourseGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.optionalCourseGroupPopupService
                .open(OptionalCourseGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
