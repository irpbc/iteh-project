import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {InputTextareaModule} from 'primeng/primeng';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    InputTextAreaDemoComponent,
    inputTextAreaDemoRoute
} from './';

const primeng_STATES = [
    inputTextAreaDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        FormsModule,
        InputTextareaModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        InputTextAreaDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectInputTextAreaDemoModule {}
