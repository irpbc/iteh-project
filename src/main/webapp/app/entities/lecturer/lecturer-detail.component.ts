import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Lecturer } from './lecturer.model';
import { LecturerService } from './lecturer.service';

@Component({
    selector: 'jhi-lecturer-detail',
    templateUrl: './lecturer-detail.component.html'
})
export class LecturerDetailComponent implements OnInit, OnDestroy {

    lecturer: Lecturer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lecturerService: LecturerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLecturers();
    }

    load(id) {
        this.lecturerService.find(id).subscribe((lecturer) => {
            this.lecturer = lecturer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLecturers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lecturerListModification',
            (response) => this.load(this.lecturer.id)
        );
    }
}
