import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FacebookPostProposal } from './facebook-post-proposal.model';
import { FacebookPostProposalPopupService } from './facebook-post-proposal-popup.service';
import { FacebookPostProposalService } from './facebook-post-proposal.service';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';
import { UserType } from '../../shared/user/user.model';

@Component({
    selector: 'jhi-facebook-post-proposal-dialog',
    templateUrl: './facebook-post-proposal-dialog.component.html'
})
export class FacebookPostProposalDialogComponent implements OnInit {

    facebookPostProposal: FacebookPostProposal;
    isSaving: boolean;

    users: User[];

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private facebookPostProposalService: FacebookPostProposalService,
                private userService: UserService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query({ filter: { 'userType.equals': UserType.ST } }).subscribe(
            (res: ResponseWrapper) => this.users = res.json,
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.facebookPostProposal.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facebookPostProposalService.update(this.facebookPostProposal));
        } else {
            this.subscribeToSaveResponse(
                this.facebookPostProposalService.create(this.facebookPostProposal));
        }
    }

    private subscribeToSaveResponse(result: Observable<FacebookPostProposal>) {
        result.subscribe((res: FacebookPostProposal) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FacebookPostProposal) {
        this.eventManager.broadcast({ name: 'facebookPostProposalListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-facebook-post-proposal-popup',
    template: ''
})
export class FacebookPostProposalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private facebookPostProposalPopupService: FacebookPostProposalPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.facebookPostProposalPopupService
                    .open(FacebookPostProposalDialogComponent as Component, params['id']);
            } else {
                this.facebookPostProposalPopupService
                    .open(FacebookPostProposalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
