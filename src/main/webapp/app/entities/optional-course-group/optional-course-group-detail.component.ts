import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OptionalCourseGroup } from './optional-course-group.model';
import { OptionalCourseGroupService } from './optional-course-group.service';

@Component({
    selector: 'jhi-optional-course-group-detail',
    templateUrl: './optional-course-group-detail.component.html'
})
export class OptionalCourseGroupDetailComponent implements OnInit, OnDestroy {

    optionalCourseGroup: OptionalCourseGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private optionalCourseGroupService: OptionalCourseGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOptionalCourseGroups();
    }

    load(id) {
        this.optionalCourseGroupService.find(id).subscribe((optionalCourseGroup) => {
            this.optionalCourseGroup = optionalCourseGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOptionalCourseGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'optionalCourseGroupListModification',
            (response) => this.load(this.optionalCourseGroup.id)
        );
    }
}
