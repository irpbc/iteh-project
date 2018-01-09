import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SchoolYear } from './school-year.model';
import { SchoolYearService } from './school-year.service';

@Injectable()
export class SchoolYearPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private schoolYearService: SchoolYearService

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
                this.schoolYearService.find(id).subscribe((schoolYear) => {
                    if (schoolYear.startDate) {
                        schoolYear.startDate = {
                            year: schoolYear.startDate.getFullYear(),
                            month: schoolYear.startDate.getMonth() + 1,
                            day: schoolYear.startDate.getDate()
                        };
                    }
                    if (schoolYear.endDate) {
                        schoolYear.endDate = {
                            year: schoolYear.endDate.getFullYear(),
                            month: schoolYear.endDate.getMonth() + 1,
                            day: schoolYear.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.schoolYearModalRef(component, schoolYear);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.schoolYearModalRef(component, new SchoolYear());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    schoolYearModalRef(component: Component, schoolYear: SchoolYear): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schoolYear = schoolYear;
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
