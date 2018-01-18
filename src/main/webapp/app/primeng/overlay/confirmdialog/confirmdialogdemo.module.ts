import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ItehProjectSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {ButtonModule} from 'primeng/primeng';
import {ConfirmDialogModule} from 'primeng/components/confirmdialog/confirmdialog';
import {ConfirmationService} from 'primeng/components/common/api';
import {APP_BASE_HREF} from '@angular/common';

import {
    ConfirmDialogDemoComponent,
    confirmDialogDemoRoute
} from './';

const primeng_STATES = [
    confirmDialogDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        CommonModule,
        ConfirmDialogModule,
        BrowserAnimationsModule,
        GrowlModule,
        ButtonModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ConfirmDialogDemoComponent
    ],
    providers: [{provide: APP_BASE_HREF, useValue: '/'}, ConfirmationService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectConfirmDialogDemoModule {}
