import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { FacebookPostProposal } from './facebook-post-proposal.model';
import { FacebookPostProposalService } from './facebook-post-proposal.service';

@Injectable()
export class FacebookPostProposalPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private facebookPostProposalService: FacebookPostProposalService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.facebookPostProposalService.find(id).subscribe((facebookPostProposal) => {
                    facebookPostProposal.time = this.datePipe
                        .transform(facebookPostProposal.time, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.facebookPostProposalModalRef(component, facebookPostProposal);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.facebookPostProposalModalRef(component, new FacebookPostProposal());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    facebookPostProposalModalRef(component: Component, facebookPostProposal: FacebookPostProposal): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facebookPostProposal = facebookPostProposal;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
