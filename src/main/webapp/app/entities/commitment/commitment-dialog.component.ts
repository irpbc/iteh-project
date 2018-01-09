import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Commitment } from './commitment.model';
import { CommitmentPopupService } from './commitment-popup.service';
import { CommitmentService } from './commitment.service';
import { Course, CourseService } from '../course';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-commitment-dialog',
    templateUrl: './commitment-dialog.component.html'
})
export class CommitmentDialogComponent implements OnInit {

    commitment: Commitment;
    isSaving: boolean;

    courses: Course[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private commitmentService: CommitmentService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.courseService.query()
            .subscribe((res: ResponseWrapper) => { this.courses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.commitment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.commitmentService.update(this.commitment));
        } else {
            this.subscribeToSaveResponse(
                this.commitmentService.create(this.commitment));
        }
    }

    private subscribeToSaveResponse(result: Observable<Commitment>) {
        result.subscribe((res: Commitment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Commitment) {
        this.eventManager.broadcast({ name: 'commitmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCourseById(index: number, item: Course) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-commitment-popup',
    template: ''
})
export class CommitmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private commitmentPopupService: CommitmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.commitmentPopupService
                    .open(CommitmentDialogComponent as Component, params['id']);
            } else {
                this.commitmentPopupService
                    .open(CommitmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
