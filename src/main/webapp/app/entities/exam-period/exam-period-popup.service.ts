import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ExamPeriod } from './exam-period.model';
import { ExamPeriodService } from './exam-period.service';

@Injectable()
export class ExamPeriodPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private examPeriodService: ExamPeriodService

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
                this.examPeriodService.find(id).subscribe((examPeriod) => {
                    if (examPeriod.startDate) {
                        examPeriod.startDate = {
                            year: examPeriod.startDate.getFullYear(),
                            month: examPeriod.startDate.getMonth() + 1,
                            day: examPeriod.startDate.getDate()
                        };
                    }
                    if (examPeriod.endDate) {
                        examPeriod.endDate = {
                            year: examPeriod.endDate.getFullYear(),
                            month: examPeriod.endDate.getMonth() + 1,
                            day: examPeriod.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.examPeriodModalRef(component, examPeriod);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.examPeriodModalRef(component, new ExamPeriod());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    examPeriodModalRef(component: Component, examPeriod: ExamPeriod): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.examPeriod = examPeriod;
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
