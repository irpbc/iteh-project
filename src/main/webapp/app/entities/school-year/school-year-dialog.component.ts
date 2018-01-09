import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SchoolYear } from './school-year.model';
import { SchoolYearPopupService } from './school-year-popup.service';
import { SchoolYearService } from './school-year.service';

@Component({
    selector: 'jhi-school-year-dialog',
    templateUrl: './school-year-dialog.component.html'
})
export class SchoolYearDialogComponent implements OnInit {

    schoolYear: SchoolYear;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private schoolYearService: SchoolYearService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schoolYear.id !== undefined) {
            this.subscribeToSaveResponse(
                this.schoolYearService.update(this.schoolYear));
        } else {
            this.subscribeToSaveResponse(
                this.schoolYearService.create(this.schoolYear));
        }
    }

    private subscribeToSaveResponse(result: Observable<SchoolYear>) {
        result.subscribe((res: SchoolYear) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SchoolYear) {
        this.eventManager.broadcast({ name: 'schoolYearListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-school-year-popup',
    template: ''
})
export class SchoolYearPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schoolYearPopupService: SchoolYearPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.schoolYearPopupService
                    .open(SchoolYearDialogComponent as Component, params['id']);
            } else {
                this.schoolYearPopupService
                    .open(SchoolYearDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
