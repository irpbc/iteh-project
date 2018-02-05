/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { ItehProjectTestModule } from '../../../test.module';
import { FacebookPostProposalComponent } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.component';
import { FacebookPostProposalService } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.service';
import { FacebookPostProposal } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.model';

describe('Component Tests', () => {

    describe('FacebookPostProposal Management Component', () => {
        let comp: FacebookPostProposalComponent;
        let fixture: ComponentFixture<FacebookPostProposalComponent>;
        let service: FacebookPostProposalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [FacebookPostProposalComponent],
                providers: [
                    FacebookPostProposalService
                ]
            })
            .overrideTemplate(FacebookPostProposalComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacebookPostProposalComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacebookPostProposalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FacebookPostProposal(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.facebookPostProposals[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
