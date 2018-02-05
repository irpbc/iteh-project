import { Component } from '@angular/core';

import { FacebookPostProposal } from './facebook-post-proposal.model';
import { FacebookPostProposalService } from './facebook-post-proposal.service';

@Component({
    selector: 'jhi-facebook-post-proposal-delete-dialog',
    template: `<delete-dialog [service]="facebookPostProposalService"
                              [entityName]="'facebookPostProposal'"></delete-dialog>`
})
export class FacebookPostProposalDeleteDialogComponent {

    facebookPostProposal: FacebookPostProposal;

    constructor(public facebookPostProposalService: FacebookPostProposalService) {
    }
}
