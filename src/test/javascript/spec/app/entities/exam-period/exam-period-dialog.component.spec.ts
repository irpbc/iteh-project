/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { ExamPeriodDialogComponent } from '../../../../../../main/webapp/app/entities/exam-period/exam-period-dialog.component';
import { ExamPeriodService } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.service';
import { ExamPeriod } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.model';
import { SchoolYearService } from '../../../../../../main/webapp/app/entities/school-year';

describe('Component Tests', () => {

    describe('ExamPeriod Management Dialog Component', () => {
        let comp: ExamPeriodDialogComponent;
        let fixture: ComponentFixture<ExamPeriodDialogComponent>;
        let service: ExamPeriodService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [ExamPeriodDialogComponent],
                providers: [
                    SchoolYearService,
                    ExamPeriodService
                ]
            })
            .overrideTemplate(ExamPeriodDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamPeriodDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamPeriodService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExamPeriod(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.examPeriod = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'examPeriodListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ExamPeriod();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.examPeriod = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'examPeriodListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
