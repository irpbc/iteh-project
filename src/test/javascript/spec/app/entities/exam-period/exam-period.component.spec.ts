/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { ExamPeriodComponent } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.component';
import { ExamPeriodService } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.service';
import { ExamPeriod } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.model';

describe('Component Tests', () => {

    describe('ExamPeriod Management Component', () => {
        let comp: ExamPeriodComponent;
        let fixture: ComponentFixture<ExamPeriodComponent>;
        let service: ExamPeriodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [ExamPeriodComponent],
                providers: [
                    ExamPeriodService
                ]
            })
            .overrideTemplate(ExamPeriodComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamPeriodComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamPeriodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ExamPeriod(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.examPeriods[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
