import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ItehProjectSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {PanelMenuModule} from 'primeng/components/panelmenu/panelmenu';

import {
    PanelMenuDemoComponent,
    panelmenuDemoRoute
} from './';

const primeng_STATES = [
    panelmenuDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        PanelMenuModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        PanelMenuDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectPanelMenuDemoModule {}
