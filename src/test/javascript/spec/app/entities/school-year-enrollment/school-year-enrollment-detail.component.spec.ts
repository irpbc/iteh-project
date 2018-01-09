/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearEnrollmentDetailComponent } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment-detail.component';
import { SchoolYearEnrollmentService } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.service';
import { SchoolYearEnrollment } from '../../../../../../main/webapp/app/entities/school-year-enrollment/school-year-enrollment.model';

describe('Component Tests', () => {

    describe('SchoolYearEnrollment Management Detail Component', () => {
        let comp: SchoolYearEnrollmentDetailComponent;
        let fixture: ComponentFixture<SchoolYearEnrollmentDetailComponent>;
        let service: SchoolYearEnrollmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearEnrollmentDetailComponent],
                providers: [
                    SchoolYearEnrollmentService
                ]
            })
            .overrideTemplate(SchoolYearEnrollmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearEnrollmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearEnrollmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SchoolYearEnrollment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.schoolYearEnrollment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
