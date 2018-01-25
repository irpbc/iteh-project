import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Commitment e2e test', () => {

    let navBarPage: NavBarPage;
    let commitmentDialogPage: CommitmentDialogPage;
    let commitmentComponentsPage: CommitmentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Commitments', () => {
        navBarPage.goToEntity('commitment');
        commitmentComponentsPage = new CommitmentComponentsPage();
        expect(commitmentComponentsPage.getTitle()).toMatch(/app.commitment.home.title/);

    });

    it('should load create Commitment dialog', () => {
        commitmentComponentsPage.clickOnCreateButton();
        commitmentDialogPage = new CommitmentDialogPage();
        expect(commitmentDialogPage.getModalTitle()).toMatch(/app.commitment.home.createOrEditLabel/);
        commitmentDialogPage.close();
    });

   /* it('should create and save Commitments', () => {
        commitmentComponentsPage.clickOnCreateButton();
        commitmentDialogPage.setNameInput('name');
        expect(commitmentDialogPage.getNameInput()).toMatch('name');
        commitmentDialogPage.setMaxPointsInput('5');
        expect(commitmentDialogPage.getMaxPointsInput()).toMatch('5');
        commitmentDialogPage.courseSelectLastOption();
        commitmentDialogPage.save();
        expect(commitmentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommitmentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-commitment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CommitmentDialogPage {
    modalTitle = element(by.css('h4#myCommitmentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    maxPointsInput = element(by.css('input#field_maxPoints'));
    courseSelect = element(by.css('select#field_course'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setMaxPointsInput = function(maxPoints) {
        this.maxPointsInput.sendKeys(maxPoints);
    };

    getMaxPointsInput = function() {
        return this.maxPointsInput.getAttribute('value');
    };

    courseSelectLastOption = function() {
        this.courseSelect.all(by.tagName('option')).last().click();
    };

    courseSelectOption = function(option) {
        this.courseSelect.sendKeys(option);
    };

    getCourseSelect = function() {
        return this.courseSelect;
    };

    getCourseSelectedOption = function() {
        return this.courseSelect.element(by.css('option:checked')).getText();
    };

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
