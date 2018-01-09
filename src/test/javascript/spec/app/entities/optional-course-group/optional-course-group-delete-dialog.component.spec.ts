/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { OptionalCourseGroupDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group-delete-dialog.component';
import { OptionalCourseGroupService } from '../../../../../../main/webapp/app/entities/optional-course-group/optional-course-group.service';

describe('Component Tests', () => {

    describe('OptionalCourseGroup Management Delete Component', () => {
        let comp: OptionalCourseGroupDeleteDialogComponent;
        let fixture: ComponentFixture<OptionalCourseGroupDeleteDialogComponent>;
        let service: OptionalCourseGroupService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [OptionalCourseGroupDeleteDialogComponent],
                providers: [
                    OptionalCourseGroupService
                ]
            })
            .overrideTemplate(OptionalCourseGroupDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OptionalCourseGroupDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalCourseGroupService);
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
