/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { StudentCommitmentComponent } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.component';
import { StudentCommitmentService } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.service';
import { StudentCommitment } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.model';

describe('Component Tests', () => {

    describe('StudentCommitment Management Component', () => {
        let comp: StudentCommitmentComponent;
        let fixture: ComponentFixture<StudentCommitmentComponent>;
        let service: StudentCommitmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [StudentCommitmentComponent],
                providers: [
                    StudentCommitmentService
                ]
            })
            .overrideTemplate(StudentCommitmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentCommitmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentCommitmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new StudentCommitment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.studentCommitments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
