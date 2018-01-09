/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { StudentExamDetailComponent } from '../../../../../../main/webapp/app/entities/student-exam/student-exam-detail.component';
import { StudentExamService } from '../../../../../../main/webapp/app/entities/student-exam/student-exam.service';
import { StudentExam } from '../../../../../../main/webapp/app/entities/student-exam/student-exam.model';

describe('Component Tests', () => {

    describe('StudentExam Management Detail Component', () => {
        let comp: StudentExamDetailComponent;
        let fixture: ComponentFixture<StudentExamDetailComponent>;
        let service: StudentExamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [StudentExamDetailComponent],
                providers: [
                    StudentExamService
                ]
            })
            .overrideTemplate(StudentExamDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentExamDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentExamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new StudentExam(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.studentExam).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
