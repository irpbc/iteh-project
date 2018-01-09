import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Commitment } from './commitment.model';
import { CommitmentService } from './commitment.service';

@Component({
    selector: 'jhi-commitment-detail',
    templateUrl: './commitment-detail.component.html'
})
export class CommitmentDetailComponent implements OnInit, OnDestroy {

    commitment: Commitment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private commitmentService: CommitmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCommitments();
    }

    load(id) {
        this.commitmentService.find(id).subscribe((commitment) => {
            this.commitment = commitment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCommitments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'commitmentListModification',
            (response) => this.load(this.commitment.id)
        );
    }
}
