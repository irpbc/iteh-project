import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Course } from './course.model';
import { CoursePopupService } from './course-popup.service';
import { CourseService } from './course.service';
import { Semester, SemesterService } from '../semester';
import { OptionalCourseGroup, OptionalCourseGroupService } from '../optional-course-group';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-course-dialog',
    templateUrl: './course-dialog.component.html'
})
export class CourseDialogComponent implements OnInit {

    course: Course;
    isSaving: boolean;

    semesters: Semester[];

    optionalcoursegroups: OptionalCourseGroup[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private courseService: CourseService,
        private semesterService: SemesterService,
        private optionalCourseGroupService: OptionalCourseGroupService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.semesterService.query()
            .subscribe((res: ResponseWrapper) => { this.semesters = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.optionalCourseGroupService.query()
            .subscribe((res: ResponseWrapper) => { this.optionalcoursegroups = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.course.id !== undefined) {
            this.subscribeToSaveResponse(
                this.courseService.update(this.course));
        } else {
            this.subscribeToSaveResponse(
                this.courseService.create(this.course));
        }
    }

    private subscribeToSaveResponse(result: Observable<Course>) {
        result.subscribe((res: Course) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Course) {
        this.eventManager.broadcast({ name: 'courseListModification', content: 'OK'});
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

    trackOptionalCourseGroupById(index: number, item: OptionalCourseGroup) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-course-popup',
    template: ''
})
export class CoursePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coursePopupService: CoursePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coursePopupService
                    .open(CourseDialogComponent as Component, params['id']);
            } else {
                this.coursePopupService
                    .open(CourseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
