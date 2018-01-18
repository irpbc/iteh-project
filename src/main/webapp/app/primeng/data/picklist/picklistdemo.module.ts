import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import {APP_BASE_HREF} from '@angular/common';

import { ItehProjectSharedModule } from '../../../shared';
import {PickListModule} from 'primeng/components/picklist/picklist';
import {GrowlModule} from 'primeng/components/growl/growl';
import {CountryService} from './service/country.service';

import {
    PicklistDemoComponent,
    picklistDemoRoute
} from './';

const primeng_STATES = [
    picklistDemoRoute
];

@NgModule({
    imports: [
        ItehProjectSharedModule,
        FormsModule,
        HttpModule,
        PickListModule,
        GrowlModule,
        RouterModule.forRoot(primeng_STATES, { useHash: true })
    ],
    providers: [{provide: APP_BASE_HREF, useValue: '/'}, CountryService],
    declarations: [
        PicklistDemoComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ItehProjectPickListDemoModule {}
