/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { CourseEnrollmentComponent } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.component';
import { CourseEnrollmentService } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.service';
import { CourseEnrollment } from '../../../../../../main/webapp/app/entities/course-enrollment/course-enrollment.model';

describe('Component Tests', () => {

    describe('CourseEnrollment Management Component', () => {
        let comp: CourseEnrollmentComponent;
        let fixture: ComponentFixture<CourseEnrollmentComponent>;
        let service: CourseEnrollmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CourseEnrollmentComponent],
                providers: [
                    CourseEnrollmentService
                ]
            })
            .overrideTemplate(CourseEnrollmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CourseEnrollmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourseEnrollmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CourseEnrollment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.courseEnrollments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
