/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearEnrollmentDialogComponent } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment-dialog.component';
import { SchoolYearEnrollmentService } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.service';
import { SchoolYearEnrollment } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.model';
import { StudentService } from '../../../../../../main/webapp/app/entities/student';
import { SchoolYearService } from '../../../../../../main/webapp/app/entities/school-year';

describe('Component Tests', () => {

    describe('SchoolYearEnrollment Management Dialog Component', () => {
        let comp: SchoolYearEnrollmentDialogComponent;
        let fixture: ComponentFixture<SchoolYearEnrollmentDialogComponent>;
        let service: SchoolYearEnrollmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearEnrollmentDialogComponent],
                providers: [
                    StudentService,
                    SchoolYearService,
                    SchoolYearEnrollmentService
                ]
            })
            .overrideTemplate(SchoolYearEnrollmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearEnrollmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearEnrollmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SchoolYearEnrollment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.schoolYearEnrollment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'schoolYearEnrollmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SchoolYearEnrollment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.schoolYearEnrollment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'schoolYearEnrollmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
