/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { LecturerDialogComponent } from '../../../../../../main/webapp/app/entities/lecturer/lecturer-dialog.component';
import { LecturerService } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.service';
import { Lecturer } from '../../../../../../main/webapp/app/entities/lecturer/lecturer.model';
import { CourseService } from '../../../../../../main/webapp/app/entities/course';

describe('Component Tests', () => {

    describe('Lecturer Management Dialog Component', () => {
        let comp: LecturerDialogComponent;
        let fixture: ComponentFixture<LecturerDialogComponent>;
        let service: LecturerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [LecturerDialogComponent],
                providers: [
                    CourseService,
                    LecturerService
                ]
            })
            .overrideTemplate(LecturerDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LecturerDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Lecturer(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.lecturer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lecturerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Lecturer();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.lecturer = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'lecturerListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
