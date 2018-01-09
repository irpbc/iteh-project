/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { CourseEnrollmentDetailComponent } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment-detail.component';
import { CourseEnrollmentService } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.service';
import { CourseEnrollment } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.model';

describe('Component Tests', () => {

    describe('CourseEnrollment Management Detail Component', () => {
        let comp: CourseEnrollmentDetailComponent;
        let fixture: ComponentFixture<CourseEnrollmentDetailComponent>;
        let service: CourseEnrollmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CourseEnrollmentDetailComponent],
                providers: [
                    CourseEnrollmentService
                ]
            })
            .overrideTemplate(CourseEnrollmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseEnrollmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseEnrollmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CourseEnrollment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.courseEnrollment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
