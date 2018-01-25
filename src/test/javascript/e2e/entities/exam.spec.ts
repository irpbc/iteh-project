import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Exam e2e test', () => {

    let navBarPage: NavBarPage;
    let examDialogPage: ExamDialogPage;
    let examComponentsPage: ExamComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Exams', () => {
        navBarPage.goToEntity('exam');
        examComponentsPage = new ExamComponentsPage();
        expect(examComponentsPage.getTitle()).toMatch(/app.exam.home.title/);

    });

    it('should load create Exam dialog', () => {
        examComponentsPage.clickOnCreateButton();
        examDialogPage = new ExamDialogPage();
        expect(examDialogPage.getModalTitle()).toMatch(/app.exam.home.createOrEditLabel/);
        examDialogPage.close();
    });

   /* it('should create and save Exams', () => {
        examComponentsPage.clickOnCreateButton();
        examDialogPage.setTimeInput(12310020012301);
        expect(examDialogPage.getTimeInput()).toMatch('2001-12-31T02:30');
        examDialogPage.periodSelectLastOption();
        examDialogPage.courseSelectLastOption();
        examDialogPage.save();
        expect(examDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ExamComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-exam div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ExamDialogPage {
    modalTitle = element(by.css('h4#myExamLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    timeInput = element(by.css('input#field_time'));
    periodSelect = element(by.css('select#field_period'));
    courseSelect = element(by.css('select#field_course'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTimeInput = function(time) {
        this.timeInput.sendKeys(time);
    };

    getTimeInput = function() {
        return this.timeInput.getAttribute('value');
    };

    periodSelectLastOption = function() {
        this.periodSelect.all(by.tagName('option')).last().click();
    };

    periodSelectOption = function(option) {
        this.periodSelect.sendKeys(option);
    };

    getPeriodSelect = function() {
        return this.periodSelect;
    };

    getPeriodSelectedOption = function() {
        return this.periodSelect.element(by.css('option:checked')).getText();
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
