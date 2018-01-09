/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { SemesterDialogComponent } from '../../../../../../main/webapp/app/entities/semester/semester-dialog.component';
import { SemesterService } from '../../../../../../main/webapp/app/entities/semester/semester.service';
import { Semester } from '../../../../../../main/webapp/app/entities/semester/semester.model';
import { SchoolYearService } from '../../../../../../main/webapp/app/entities/school-year';

describe('Component Tests', () => {

    describe('Semester Management Dialog Component', () => {
        let comp: SemesterDialogComponent;
        let fixture: ComponentFixture<SemesterDialogComponent>;
        let service: SemesterService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SemesterDialogComponent],
                providers: [
                    SchoolYearService,
                    SemesterService
                ]
            })
            .overrideTemplate(SemesterDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SemesterDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SemesterService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Semester(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.semester = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'semesterListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Semester();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.semester = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'semesterListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
