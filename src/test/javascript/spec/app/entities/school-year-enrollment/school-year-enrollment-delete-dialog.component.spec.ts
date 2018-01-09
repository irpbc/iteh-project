/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearEnrollmentDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment-delete-dialog.component';
import { SchoolYearEnrollmentService } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.service';

describe('Component Tests', () => {

    describe('SchoolYearEnrollment Management Delete Component', () => {
        let comp: SchoolYearEnrollmentDeleteDialogComponent;
        let fixture: ComponentFixture<SchoolYearEnrollmentDeleteDialogComponent>;
        let service: SchoolYearEnrollmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearEnrollmentDeleteDialogComponent],
                providers: [
                    SchoolYearEnrollmentService
                ]
            })
            .overrideTemplate(SchoolYearEnrollmentDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearEnrollmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearEnrollmentService);
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
