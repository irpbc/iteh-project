/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearEnrollmentComponent } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.component';
import { SchoolYearEnrollmentService } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.service';
import { SchoolYearEnrollment } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.model';

describe('Component Tests', () => {

    describe('SchoolYearEnrollment Management Component', () => {
        let comp: SchoolYearEnrollmentComponent;
        let fixture: ComponentFixture<SchoolYearEnrollmentComponent>;
        let service: SchoolYearEnrollmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearEnrollmentComponent],
                providers: [
                    SchoolYearEnrollmentService
                ]
            })
            .overrideTemplate(SchoolYearEnrollmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearEnrollmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearEnrollmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SchoolYearEnrollment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.schoolYearEnrollments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
