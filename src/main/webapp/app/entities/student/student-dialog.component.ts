import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import {
    FindLanguageFromKeyPipe,
    JhiLanguageHelper,
    User,
    UserService,
    UserType
} from '../../shared';
import { StudentPopupService } from './student-popup.service';

@Component({
    selector: 'jhi-student-dialog',
    templateUrl: './student-dialog.component.html'
})
export class StudentDialogComponent implements OnInit {

    student: User;
    languages: any[];
    isSaving: Boolean;

    constructor(public activeModal: NgbActiveModal,
                private languageHelper: JhiLanguageHelper,
                private userService: UserService,
                private eventManager: JhiEventManager,
                private langPipe: FindLanguageFromKeyPipe) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.languages = this.languageHelper.getAll();
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.student.userType = UserType.ST;
        this.student.activated = true;
        if (this.student.id !== null) {
            this.userService.update(this.student).subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            this.userService.create(this.student).subscribe((response) => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    translateLang = (langKey: string): string => {
        return this.langPipe.transform(langKey);
    };

    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'studentListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-student-popup',
    template: ''
})
export class StudentDialogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private popupService: StudentPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.popupService.open(StudentDialogComponent as Component, params['id']);
            } else {
                this.popupService.open(StudentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
