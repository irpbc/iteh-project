import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/primeng';
import {CheckboxModule} from 'primeng/components/checkbox/checkbox';
import {RadioButtonModule} from 'primeng/components/radiobutton/radiobutton';
import {ButtonModule} from 'primeng/components/button/button';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    InputGroupDemoComponent,
    inputGroupDemoRoute
} from './';

const primeng_STATES = [
    inputGroupDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        FormsModule,
        InputTextModule,
        CheckboxModule,
        RadioButtonModule,
        ButtonModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        InputGroupDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectInputGroupDemoModule {}
