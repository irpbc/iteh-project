/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { StudentCommitmentDetailComponent } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment-detail.component';
import { StudentCommitmentService } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.service';
import { StudentCommitment } from '../../../../../../main/webapp/app/entities/student-commitment/student-commitment.model';

describe('Component Tests', () => {

    describe('StudentCommitment Management Detail Component', () => {
        let comp: StudentCommitmentDetailComponent;
        let fixture: ComponentFixture<StudentCommitmentDetailComponent>;
        let service: StudentCommitmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [StudentCommitmentDetailComponent],
                providers: [
                    StudentCommitmentService
                ]
            })
            .overrideTemplate(StudentCommitmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StudentCommitmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StudentCommitmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new StudentCommitment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.studentCommitment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
