/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { CourseEnrollmentDialogComponent } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment-dialog.component';
import { CourseEnrollmentService } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.service';
import { CourseEnrollment } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.model';
import { SchoolYearEnrollmentService } from '../../../../../../main/webapp/app/entities/school-year-enrollment';
import { CourseService } from '../../../../../../main/webapp/app/entities/course';

describe('Component Tests', () => {

    describe('CourseEnrollment Management Dialog Component', () => {
        let comp: CourseEnrollmentDialogComponent;
        let fixture: ComponentFixture<CourseEnrollmentDialogComponent>;
        let service: CourseEnrollmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CourseEnrollmentDialogComponent],
                providers: [
                    SchoolYearEnrollmentService,
                    CourseService,
                    CourseEnrollmentService
                ]
            })
            .overrideTemplate(CourseEnrollmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseEnrollmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseEnrollmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CourseEnrollment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.courseEnrollment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'courseEnrollmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CourseEnrollment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.courseEnrollment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'courseEnrollmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
