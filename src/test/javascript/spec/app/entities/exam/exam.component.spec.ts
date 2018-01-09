/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { ExamComponent } from '../../../../../../main/webapp/app/entities/exam/exam.component';
import { ExamService } from '../../../../../../main/webapp/app/entities/exam/exam.service';
import { Exam } from '../../../../../../main/webapp/app/entities/exam/exam.model';

describe('Component Tests', () => {

    describe('Exam Management Component', () => {
        let comp: ExamComponent;
        let fixture: ComponentFixture<ExamComponent>;
        let service: ExamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [ExamComponent],
                providers: [
                    ExamService
                ]
            })
            .overrideTemplate(ExamComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Exam(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.exams[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
