import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ItehProjectSharedModule } from '../../../shared';
import {PaginatorModule} from 'primeng/primeng';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    PaginatorDemoComponent,
    paginatorDemoRoute
} from './';

const primeng_STATES = [
    paginatorDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        PaginatorModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        PaginatorDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectPaginatorDemoModule {}
