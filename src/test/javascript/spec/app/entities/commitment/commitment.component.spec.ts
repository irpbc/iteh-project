/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { CommitmentComponent } from '../../../../../../main/webapp/app/entities/commitment/commitment.component';
import { CommitmentService } from '../../../../../../main/webapp/app/entities/commitment/commitment.service';
import { Commitment } from '../../../../../../main/webapp/app/entities/commitment/commitment.model';

describe('Component Tests', () => {

    describe('Commitment Management Component', () => {
        let comp: CommitmentComponent;
        let fixture: ComponentFixture<CommitmentComponent>;
        let service: CommitmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [CommitmentComponent],
                providers: [
                    CommitmentService
                ]
            })
            .overrideTemplate(CommitmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommitmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommitmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Commitment(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commitments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
