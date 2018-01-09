/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { ExamPeriodDetailComponent } from '../../../../../../main/webapp/app/entities/exam-period/exam-period-detail.component';
import { ExamPeriodService } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.service';
import { ExamPeriod } from '../../../../../../main/webapp/app/entities/exam-period/exam-period.model';

describe('Component Tests', () => {

    describe('ExamPeriod Management Detail Component', () => {
        let comp: ExamPeriodDetailComponent;
        let fixture: ComponentFixture<ExamPeriodDetailComponent>;
        let service: ExamPeriodService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [ExamPeriodDetailComponent],
                providers: [
                    ExamPeriodService
                ]
            })
            .overrideTemplate(ExamPeriodDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamPeriodDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamPeriodService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ExamPeriod(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.examPeriod).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
