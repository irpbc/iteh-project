/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { OptionalCourseGroupDialogComponent } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group-dialog.component';
import { OptionalCourseGroupService } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.service';
import { OptionalCourseGroup } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.model';
import { SemesterService } from '../../../../../../main/webapp/app/entities/semester';

describe('Component Tests', () => {

    describe('OptionalCourseGroup Management Dialog Component', () => {
        let comp: OptionalCourseGroupDialogComponent;
        let fixture: ComponentFixture<OptionalCourseGroupDialogComponent>;
        let service: OptionalCourseGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [OptionalCourseGroupDialogComponent],
                providers: [
                    SemesterService,
                    OptionalCourseGroupService
                ]
            })
            .overrideTemplate(OptionalCourseGroupDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OptionalCourseGroupDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalCourseGroupService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OptionalCourseGroup(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.optionalCourseGroup = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'optionalCourseGroupListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new OptionalCourseGroup();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.optionalCourseGroup = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'optionalCourseGroupListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
