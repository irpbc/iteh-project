import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { ItehProjectSharedModule, UserRouteAccessService } from './shared';
import { ItehProjectAppRoutingModule } from './app-routing.module';
import { ItehProjectHomeModule } from './home/home.module';
import { ItehProjectAdminModule } from './admin/admin.module';
import { ItehProjectAccountModule } from './account/account.module';
import { ItehProjectEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ItehProjectprimengModule } from './primeng/primeng.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        ItehProjectAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        ItehProjectSharedModule,
        ItehProjectHomeModule,
        ItehProjectAdminModule,
        ItehProjectAccountModule,
        ItehProjectEntityModule,
        BrowserAnimationsModule,
        ItehProjectprimengModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService,
    ],
    bootstrap: [JhiMainComponent]
})
export class ItehProjectAppModule {
}
