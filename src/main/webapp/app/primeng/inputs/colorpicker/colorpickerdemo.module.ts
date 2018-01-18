import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {ColorPickerModule} from 'primeng/components/colorpicker/colorpicker';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    ColorpickerDemoComponent,
    colorpickerDemoRoute
} from './';

const primeng_STATES = [
    colorpickerDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        FormsModule,
        ColorPickerModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ColorpickerDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectColorPickerDemoModule {}
