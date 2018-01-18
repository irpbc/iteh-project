import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {PasswordModule} from 'primeng/primeng';
import {ButtonModule} from 'primeng/components/button/button';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    PasswordIndicatorDemoComponent,
    passwordindicatorDemoRoute
} from './';

const primeng_STATES = [
    passwordindicatorDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        FormsModule,
        PasswordModule,
        GrowlModule,
        ButtonModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        PasswordIndicatorDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectPasswordIndicatorDemoModule {}
