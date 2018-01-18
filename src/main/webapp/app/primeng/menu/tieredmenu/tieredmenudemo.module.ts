import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {CommonModule} from '@angular/common';
import { ItehProjectSharedModule } from '../../../shared';
import {GrowlModule} from 'primeng/primeng';
import {TieredMenuModule} from 'primeng/components/tieredmenu/tieredmenu';
import {ButtonModule} from 'primeng/components/button/button';

import {
    TieredMenuDemoComponent,
    tieredmenuDemoRoute
} from './';

const primeng_STATES = [
    tieredmenuDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        CommonModule,
        BrowserAnimationsModule,
        TieredMenuModule,
        GrowlModule,
        ButtonModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        TieredMenuDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectTieredMenuDemoModule {}
