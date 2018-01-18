import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// import needed PrimeNG modules here

import { ItehProjectSharedModule } from '../../../shared';
import {FormsModule} from '@angular/forms';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    GridDemoComponent,
    gridDemoRoute
} from './';

const primeng_STATES = [
    gridDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        GridDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectGridDemoModule {}
