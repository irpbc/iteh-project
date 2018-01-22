import { Component, Input, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { BaseEntity } from '../model/base-entity';
import { EntityService } from '../entity/entity.service';
import { FieldDef } from './field-def';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'entity-edit-dialog',
    templateUrl: './entity-edit-dialog.component.html',
})
export class EntityEditDialogComponent implements OnInit, OnDestroy {

    @Input() public entityName: string;
    @Input() public fieldDefs: FieldDef[];

    @Input() public entityConstructor: new() => BaseEntity;
    @Input() public entityService: EntityService<any>;

    @ViewChild('content') templateRef: TemplateRef<any>;

    object: any;
    isSaving: boolean;

    private ngbModalRef: NgbModalRef;
    private routerSub: Subscription;

    constructor(private modalService: NgbModal,
                private router: Router,
                private route: ActivatedRoute,
                private eventManager: JhiEventManager) {
        this.ngbModalRef = null;
    }

    ngOnInit() {
        this.isSaving = false;
        this.routerSub = this.route.params.subscribe((params) => {
            this.open(params['id']);
        });
    }

    ngOnDestroy() {
        this.routerSub.unsubscribe();
    }

    open(id?: number | any) {
        const isOpen = this.ngbModalRef !== null;
        if (isOpen) {
            return;
        }
        if (id) {
            this.entityService.find(id).subscribe((object) => {
                this.object = object;
                this.ngbModalRef = this.openModal();
            });
        } else {
            // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
            setTimeout(() => {
                const entityConstructor = this.entityConstructor;
                this.object = new entityConstructor();
                this.ngbModalRef = this.openModal();
            }, 0);
        }
    }

    openModal(): NgbModalRef {
        const modalRef: NgbModalRef = this.modalService.open(this.templateRef, { size: 'lg', backdrop: 'static' });
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

    _save() {
        this.isSaving = true;
        if (this.object.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entityService.update(this.object));
        } else {
            this.subscribeToSaveResponse(
                this.entityService.create(this.object));
        }
    }

    private subscribeToSaveResponse(result: Observable<BaseEntity>) {
        result.subscribe((res: BaseEntity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BaseEntity) {
        this.eventManager.broadcast({
            name: this.entityName + 'ListModification',
            content: 'OK'
        });
        this.isSaving = false;
        this.ngbModalRef.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    _clear() {
        this.ngbModalRef.dismiss('cancel');
    }
}
