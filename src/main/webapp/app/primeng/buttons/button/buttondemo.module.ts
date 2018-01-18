import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import { ButtonModule } from 'primeng/primeng';
import { GrowlModule } from 'primeng/primeng';

import {
    ButtonDemoComponent,
    buttonDemoRoute
} from './';

const primeng_STATES = [
    buttonDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        ButtonModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ButtonDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectButtonDemoModule {
}
