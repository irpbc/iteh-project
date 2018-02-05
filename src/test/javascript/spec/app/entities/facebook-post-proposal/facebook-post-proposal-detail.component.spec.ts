/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { ItehProjectTestModule } from '../../../test.module';
import { FacebookPostProposalDetailComponent } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal-detail.component';
import { FacebookPostProposalService } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.service';
import { FacebookPostProposal } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.model';

describe('Component Tests', () => {

    describe('FacebookPostProposal Management Detail Component', () => {
        let comp: FacebookPostProposalDetailComponent;
        let fixture: ComponentFixture<FacebookPostProposalDetailComponent>;
        let service: FacebookPostProposalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [FacebookPostProposalDetailComponent],
                providers: [
                    FacebookPostProposalService
                ]
            })
            .overrideTemplate(FacebookPostProposalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacebookPostProposalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacebookPostProposalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new FacebookPostProposal(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.facebookPostProposal).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
