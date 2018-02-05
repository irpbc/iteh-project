import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FacebookPostProposal } from './facebook-post-proposal.model';
import { FacebookPostProposalService } from './facebook-post-proposal.service';

@Component({
    selector: 'jhi-facebook-post-proposal-detail',
    templateUrl: './facebook-post-proposal-detail.component.html'
})
export class FacebookPostProposalDetailComponent implements OnInit, OnDestroy {

    facebookPostProposal: FacebookPostProposal;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private facebookPostProposalService: FacebookPostProposalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacebookPostProposals();
    }

    load(id) {
        this.facebookPostProposalService.find(id).subscribe((facebookPostProposal) => {
            this.facebookPostProposal = facebookPostProposal;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacebookPostProposals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facebookPostProposalListModification',
            (response) => this.load(this.facebookPostProposal.id)
        );
    }
}
