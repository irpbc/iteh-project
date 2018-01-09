/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearDetailComponent } from '../../../../../../main/webapp/app/entities/school-year/school-year-detail.component';
import { SchoolYearService } from '../../../../../../main/webapp/app/entities/school-year/school-year.service';
import { SchoolYear } from '../../../../../../main/webapp/app/entities/school-year/school-year.model';

describe('Component Tests', () => {

    describe('SchoolYear Management Detail Component', () => {
        let comp: SchoolYearDetailComponent;
        let fixture: ComponentFixture<SchoolYearDetailComponent>;
        let service: SchoolYearService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearDetailComponent],
                providers: [
                    SchoolYearService
                ]
            })
            .overrideTemplate(SchoolYearDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SchoolYear(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.schoolYear).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
