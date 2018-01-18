import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ItehProjectSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {SlideMenuModule} from 'primeng/components/slidemenu/slidemenu';
import {ButtonModule} from 'primeng/components/button/button';

import {
    SlideMenuDemoComponent,
    slidemenuDemoRoute
} from './';

const primeng_STATES = [
    slidemenuDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        SlideMenuModule,
        GrowlModule,
        ButtonModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        SlideMenuDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectSlideMenuDemoModule {}
