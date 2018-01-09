/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { StudentExamComponent } from '../../../../../../main/webapp/app/entities/student-exam/student-exam.component';
import { StudentExamService } from '../../../../../../main/webapp/app/entities/student-exam/student-exam.service';
import { StudentExam } from '../../../../../../main/webapp/app/entities/student-exam/student-exam.model';

describe('Component Tests', () => {

    describe('StudentExam Management Component', () => {
        let comp: StudentExamComponent;
        let fixture: ComponentFixture<StudentExamComponent>;
        let service: StudentExamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [StudentExamComponent],
                providers: [
                    StudentExamService
                ]
            })
            .overrideTemplate(StudentExamComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentExamComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentExamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new StudentExam(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.studentExams[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
