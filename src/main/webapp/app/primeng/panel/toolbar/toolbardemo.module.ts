import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule} from '@angular/forms';

// import needed PrimeNG modules here

import { ItehProjectSharedModule } from '../../../shared';
import {ToolbarModule} from 'primeng/components/toolbar/toolbar';
import {InputTextModule} from 'primeng/components/inputtext/inputtext';
import {SplitButtonModule} from 'primeng/components/splitbutton/splitbutton';
import {ButtonModule} from 'primeng/components/button/button';
import {GrowlModule} from 'primeng/components/growl/growl';

import {
    ToolbarDemoComponent,
    toolbarDemoRoute
} from './';

const primeng_STATES = [
    toolbarDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ToolbarModule,
        InputTextModule,
        SplitButtonModule,
        ButtonModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    declarations: [
        ToolbarDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectToolbarDemoModule {}
