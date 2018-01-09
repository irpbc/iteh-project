/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { SchoolYearComponent } from '../../../../../../main/webapp/app/entities/school-year/school-year.component';
import { SchoolYearService } from '../../../../../../main/webapp/app/entities/school-year/school-year.service';
import { SchoolYear } from '../../../../../../main/webapp/app/entities/school-year/school-year.model';

describe('Component Tests', () => {

    describe('SchoolYear Management Component', () => {
        let comp: SchoolYearComponent;
        let fixture: ComponentFixture<SchoolYearComponent>;
        let service: SchoolYearService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [SchoolYearComponent],
                providers: [
                    SchoolYearService
                ]
            })
            .overrideTemplate(SchoolYearComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SchoolYearComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SchoolYearService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SchoolYear(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.schoolYears[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
