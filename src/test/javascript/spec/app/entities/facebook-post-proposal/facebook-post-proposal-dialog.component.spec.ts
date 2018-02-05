/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ItehProjectTestModule } from '../../../test.module';
import { FacebookPostProposalDialogComponent } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal-dialog.component';
import { FacebookPostProposalService } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.service';
import { FacebookPostProposal } from '../../../../../../main/webapp/app/entities/facebook-post-proposal/facebook-post-proposal.model';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('FacebookPostProposal Management Dialog Component', () => {
        let comp: FacebookPostProposalDialogComponent;
        let fixture: ComponentFixture<FacebookPostProposalDialogComponent>;
        let service: FacebookPostProposalService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ItehProjectTestModule],
                declarations: [FacebookPostProposalDialogComponent],
                providers: [
                    UserService,
                    FacebookPostProposalService
                ]
            })
            .overrideTemplate(FacebookPostProposalDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FacebookPostProposalDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacebookPostProposalService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FacebookPostProposal(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.facebookPostProposal = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'facebookPostProposalListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FacebookPostProposal();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.facebookPostProposal = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'facebookPostProposalListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
