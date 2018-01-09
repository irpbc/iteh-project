/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { CommitmentDetailComponent } from '../../../../../../main/webapp/app/entities/commitment/commitment-detail.component';
import { CommitmentService } from '../../../../../../main/webapp/app/entities/commitment/commitment.service';
import { Commitment } from '../../../../../../main/webapp/app/entities/commitment/commitment.model';

describe('Component Tests', () => {

    describe('Commitment Management Detail Component', () => {
        let comp: CommitmentDetailComponent;
        let fixture: ComponentFixture<CommitmentDetailComponent>;
        let service: CommitmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CommitmentDetailComponent],
                providers: [
                    CommitmentService
                ]
            })
            .overrideTemplate(CommitmentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommitmentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommitmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Commitment(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.commitment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
