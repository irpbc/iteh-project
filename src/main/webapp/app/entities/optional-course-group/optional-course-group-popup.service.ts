import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OptionalCourseGroup } from './optional-course-group.model';
import { OptionalCourseGroupService } from './optional-course-group.service';

@Injectable()
export class OptionalCourseGroupPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private optionalCourseGroupService: OptionalCourseGroupService

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
                this.optionalCourseGroupService.find(id).subscribe((optionalCourseGroup) => {
                    this.ngbModalRef = this.optionalCourseGroupModalRef(component, optionalCourseGroup);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.optionalCourseGroupModalRef(component, new OptionalCourseGroup());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    optionalCourseGroupModalRef(component: Component, optionalCourseGroup: OptionalCourseGroup): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.optionalCourseGroup = optionalCourseGroup;
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
