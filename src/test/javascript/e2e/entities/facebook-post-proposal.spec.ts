import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('FacebookPostProposal e2e test', () => {

    let navBarPage: NavBarPage;
    let facebookPostProposalDialogPage: FacebookPostProposalDialogPage;
    let facebookPostProposalComponentsPage: FacebookPostProposalComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load FacebookPostProposals', () => {
        navBarPage.goToEntity('facebook-post-proposal');
        facebookPostProposalComponentsPage = new FacebookPostProposalComponentsPage();
        expect(facebookPostProposalComponentsPage.getTitle()).toMatch(/app.facebookPostProposal.home.title/);

    });

    it('should load create FacebookPostProposal dialog', () => {
        facebookPostProposalComponentsPage.clickOnCreateButton();
        facebookPostProposalDialogPage = new FacebookPostProposalDialogPage();
        expect(facebookPostProposalDialogPage.getModalTitle()).toMatch(/app.facebookPostProposal.home.createOrEditLabel/);
        facebookPostProposalDialogPage.close();
    });

   /* it('should create and save FacebookPostProposals', () => {
        facebookPostProposalComponentsPage.clickOnCreateButton();
        facebookPostProposalDialogPage.kindSelectLastOption();
        facebookPostProposalDialogPage.setDataInput('data');
        expect(facebookPostProposalDialogPage.getDataInput()).toMatch('data');
        facebookPostProposalDialogPage.setTimeInput(12310020012301);
        expect(facebookPostProposalDialogPage.getTimeInput()).toMatch('2001-12-31T02:30');
        facebookPostProposalDialogPage.studentSelectLastOption();
        facebookPostProposalDialogPage.save();
        expect(facebookPostProposalDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FacebookPostProposalComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-facebook-post-proposal div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class FacebookPostProposalDialogPage {
    modalTitle = element(by.css('h4#myFacebookPostProposalLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    kindSelect = element(by.css('select#field_kind'));
    dataInput = element(by.css('input#field_data'));
    timeInput = element(by.css('input#field_time'));
    studentSelect = element(by.css('select#field_student'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setKindSelect = function(kind) {
        this.kindSelect.sendKeys(kind);
    }

    getKindSelect = function() {
        return this.kindSelect.element(by.css('option:checked')).getText();
    }

    kindSelectLastOption = function() {
        this.kindSelect.all(by.tagName('option')).last().click();
    }
    setDataInput = function(data) {
        this.dataInput.sendKeys(data);
    }

    getDataInput = function() {
        return this.dataInput.getAttribute('value');
    }

    setTimeInput = function(time) {
        this.timeInput.sendKeys(time);
    }

    getTimeInput = function() {
        return this.timeInput.getAttribute('value');
    }

    studentSelectLastOption = function() {
        this.studentSelect.all(by.tagName('option')).last().click();
    }

    studentSelectOption = function(option) {
        this.studentSelect.sendKeys(option);
    }

    getStudentSelect = function() {
        return this.studentSelect;
    }

    getStudentSelectedOption = function() {
        return this.studentSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
