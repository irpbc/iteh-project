import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Http, Response } from '@angular/http';
import { JhiEventManager } from 'ng-jhipster';
import { TranslateService } from '@ngx-translate/core';
import { FacebookPostProposalService } from './facebook-post-proposal.service';
import { FacebookService, UIParams } from 'ngx-facebook';

@Component({
    selector: 'app-publish-facebook-post',
    templateUrl: './publish-facebook-post.component.html'
})
export class PublishFacebookPostComponent implements OnInit, OnDestroy {

    @ViewChild('template')
    templateRef: TemplateRef<any>;

    private modalRef: NgbModalRef;
    private routeSub: Subscription;

    private id: number;

    text: string;

    constructor(private modal: NgbModal,
                private router: Router,
                private route: ActivatedRoute,
                private http: Http,
                private eventManager: JhiEventManager,
                private translate: TranslateService,
                private facebookPostProposalService: FacebookPostProposalService,
                private facebook: FacebookService) {
    }

    ngOnInit() {
        this.facebook.init({
            appId: '146176679417761',
            xfbml: true,
            version: 'v2.12'
        });

        this.routeSub = this.route.params.subscribe((params) => {
            this.id = params['id'];
            this.activate();
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    private async activate() {
        try {
            const prop = await this.facebookPostProposalService.find(this.id).toPromise();
            const key = 'app.facebookPostProposal.dataToText.' + prop.kind;
            this.text = await this.translate.get(key, prop.data).toPromise();
            await this.runModal();
            await this.runFacebookShareDialog();
        } catch (err) {
            console.log(err);
        }
        this.resetRoute();
    }

    continue() {
        this.modalRef.close();
    }

    cancel() {
        this.modalRef.dismiss('cancel');
    }

    private resetRoute() {
        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
    }

    private async runModal() {
        try {
            this.modalRef = this.modal.open(this.templateRef, { size: 'lg', backdrop: 'static' });
            await this.modalRef.result;
        } finally {
            this.modalRef = null;
        }
    }

    private runFacebookShareDialog() {
        const params: UIParams = {
            method: 'share_open_graph',
            action_type: 'og.shares',
            action_properties: JSON.stringify({
                scrape: false,
                object: {
                    'og:url': 'http://www.fon.bg.ac.rs',
                    'og:title': this.text,
                }
            })
        };
        return this.facebook.ui(params);
    }
}
