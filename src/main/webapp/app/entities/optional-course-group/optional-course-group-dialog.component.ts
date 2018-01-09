import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OptionalCourseGroup } from './optional-course-group.model';
import { OptionalCourseGroupPopupService } from './optional-course-group-popup.service';
import { OptionalCourseGroupService } from './optional-course-group.service';
import { Semester, SemesterService } from '../semester';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-optional-course-group-dialog',
    templateUrl: './optional-course-group-dialog.component.html'
})
export class OptionalCourseGroupDialogComponent implements OnInit {

    optionalCourseGroup: OptionalCourseGroup;
    isSaving: boolean;

    semesters: Semester[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private optionalCourseGroupService: OptionalCourseGroupService,
        private semesterService: SemesterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.semesterService.query()
            .subscribe((res: ResponseWrapper) => { this.semesters = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.optionalCourseGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.optionalCourseGroupService.update(this.optionalCourseGroup));
        } else {
            this.subscribeToSaveResponse(
                this.optionalCourseGroupService.create(this.optionalCourseGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<OptionalCourseGroup>) {
        result.subscribe((res: OptionalCourseGroup) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OptionalCourseGroup) {
        this.eventManager.broadcast({ name: 'optionalCourseGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSemesterById(index: number, item: Semester) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-optional-course-group-popup',
    template: ''
})
export class OptionalCourseGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private optionalCourseGroupPopupService: OptionalCourseGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.optionalCourseGroupPopupService
                    .open(OptionalCourseGroupDialogComponent as Component, params['id']);
            } else {
                this.optionalCourseGroupPopupService
                    .open(OptionalCourseGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
