/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { StudentCommitmentDialogComponent } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment-dialog.component';
import { StudentCommitmentService } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.service';
import { StudentCommitment } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.model';
import { CourseEnrollmentService } from '../../../../../../main/webapp/app/entities/course-enrollment';
import { CommitmentService } from '../../../../../../main/webapp/app/entities/commitment';
import { LecturerService } from '../../../../../../main/webapp/app/entities/lecturer';

describe('Component Tests', () => {

    describe('StudentCommitment Management Dialog Component', () => {
        let comp: StudentCommitmentDialogComponent;
        let fixture: ComponentFixture<StudentCommitmentDialogComponent>;
        let service: StudentCommitmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [StudentCommitmentDialogComponent],
                providers: [
                    CourseEnrollmentService,
                    CommitmentService,
                    LecturerService,
                    StudentCommitmentService
                ]
            })
            .overrideTemplate(StudentCommitmentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentCommitmentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentCommitmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StudentCommitment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.studentCommitment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'studentCommitmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new StudentCommitment();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.studentCommitment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'studentCommitmentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
