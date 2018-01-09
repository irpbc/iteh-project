/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { CourseEnrollmentDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment-delete-dialog.component';
import { CourseEnrollmentService } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.service';

describe('Component Tests', () => {

    describe('CourseEnrollment Management Delete Component', () => {
        let comp: CourseEnrollmentDeleteDialogComponent;
        let fixture: ComponentFixture<CourseEnrollmentDeleteDialogComponent>;
        let service: CourseEnrollmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CourseEnrollmentDeleteDialogComponent],
                providers: [
                    CourseEnrollmentService
                ]
            })
            .overrideTemplate(CourseEnrollmentDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseEnrollmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseEnrollmentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
