/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { ExamDialogComponent } from '../../../../../../main/webapp/app/entities/exam/exam-dialog.component';
import { ExamService } from '../../../../../../main/webapp/app/entities/exam/exam.service';
import { Exam } from '../../../../../../main/webapp/app/entities/exam/exam.model';
import { ExamPeriodService } from '../../../../../../main/webapp/app/entities/exam-period';
import { CourseService } from '../../../../../../main/webapp/app/entities/course';

describe('Component Tests', () => {

    describe('Exam Management Dialog Component', () => {
        let comp: ExamDialogComponent;
        let fixture: ComponentFixture<ExamDialogComponent>;
        let service: ExamService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [ExamDialogComponent],
                providers: [
                    ExamPeriodService,
                    CourseService,
                    ExamService
                ]
            })
            .overrideTemplate(ExamDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Exam(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.exam = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'examListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Exam();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.exam = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'examListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
