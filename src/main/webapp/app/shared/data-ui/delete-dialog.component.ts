import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';

import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { EntityService } from '../../shared';
import { JhiEventManager } from 'ng-jhipster';

@Component({
    selector: 'delete-dialog',
    templateUrl: './delete-dialog.component.html'
})
export class DeleteDialogComponent implements OnInit, OnDestroy {

    @Input() public entityName: string;
    @Input() public service: EntityService<any>;

    @ViewChild('content') template: TemplateRef<any>;

    public id: number;
    private routeSub: Subscription;
    private modalRef: NgbModalRef;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private modalService: NgbModal,
        private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.id = params['id'];
            this.openModal();
        });
    }

    openModal(): NgbModalRef {
        const modalRef: NgbModalRef = this.modalService.open(this.template, { size: 'lg', backdrop: 'static'});
        this.modalRef = modalRef;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.modalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.modalRef = null;
        });
        return modalRef;
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    _clear() {
        this.modalRef.dismiss('cancel');
    }

    _confirmDelete() {
        this.service.delete(this.id).subscribe((response) => {
            this.eventManager.broadcast({
                name: this.entityName + 'ListModification',
                content: 'Deleted a ' + this.entityName
            });
            this.modalRef.dismiss(true);
        });
    }
}
