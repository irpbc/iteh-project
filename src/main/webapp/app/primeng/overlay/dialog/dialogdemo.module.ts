import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {ButtonModule} from 'primeng/primeng';
import {DialogModule} from 'primeng/primeng';
import {GrowlModule} from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {
    DialogDemoComponent,
    dialogDemoRoute
} from './';

const primeng_STATES = [
    dialogDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        ButtonModule,
        DialogModule,
        GrowlModule,
        BrowserAnimationsModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        DialogDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectDialogDemoModule {}
