import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/sr';

import { WindowRef } from './tracker/window.service';
import {
    ItehProjectSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent
} from './';

@NgModule({
    imports: [
        ItehProjectSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        FindLanguageFromKeyPipe,
        WindowRef,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'sr'
        },
    ],
    exports: [
        ItehProjectSharedLibsModule,
        FindLanguageFromKeyPipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class ItehProjectSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
