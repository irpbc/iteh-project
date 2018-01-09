/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { CourseDialogComponent } from '../../../../../../main/webapp/app/entities/course/course-dialog.component';
import { CourseService } from '../../../../../../main/webapp/app/entities/course/course.service';
import { Course } from '../../../../../../main/webapp/app/entities/course/course.model';
import { SemesterService } from '../../../../../../main/webapp/app/entities/semester';
import { OptionalCourseGroupService } from '../../../../../../main/webapp/app/entities/optional-course-group';
import { LecturerService } from '../../../../../../main/webapp/app/entities/lecturer';

describe('Component Tests', () => {

    describe('Course Management Dialog Component', () => {
        let comp: CourseDialogComponent;
        let fixture: ComponentFixture<CourseDialogComponent>;
        let service: CourseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CourseDialogComponent],
                providers: [
                    SemesterService,
                    OptionalCourseGroupService,
                    LecturerService,
                    CourseService
                ]
            })
            .overrideTemplate(CourseDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Course(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.course = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'courseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Course();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.course = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'courseListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
