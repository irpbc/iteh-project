import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchoolYearEnrollment } from './school-year-enrollment.model';
import { SchoolYearEnrollmentService } from './school-year-enrollment.service';

@Injectable()
export class SchoolYearEnrollmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schoolYearEnrollmentService: SchoolYearEnrollmentService

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
                this.schoolYearEnrollmentService.find(id).subscribe((schoolYearEnrollment) => {
                    this.ngbModalRef = this.schoolYearEnrollmentModalRef(component, schoolYearEnrollment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.schoolYearEnrollmentModalRef(component, new SchoolYearEnrollment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    schoolYearEnrollmentModalRef(component: Component, schoolYearEnrollment: SchoolYearEnrollment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schoolYearEnrollment = schoolYearEnrollment;
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
